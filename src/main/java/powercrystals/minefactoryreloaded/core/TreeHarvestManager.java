package powercrystals.minefactoryreloaded.core;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.api.plant.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;
import powercrystals.minefactoryreloaded.core.BlockPool.BlockNode;

import java.util.Map;

public class TreeHarvestManager implements IHarvestManager {

	private BlockPool _blocks;
	private boolean _isDone;

	private IFactorySettings _settings;
	private HarvestMode _harvestMode;
	private Area _area;
	private World _world;

	private BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

	public TreeHarvestManager(NBTTagCompound tag, IFactorySettings s) {

		readFromNBT(tag);
		_settings = s;
	}

	public TreeHarvestManager(World world, Area treeArea, HarvestMode harvestMode, IFactorySettings s) {

		reset(world, treeArea, harvestMode, s);
		_isDone = true;
	}

	@Override
	public BlockPos getNextBlock() {

		BlockNode bn = _blocks.shift();
		searchForTreeBlocks(bn);
		BlockPos bp = pos.setPos(bn.x, bn.y, bn.z);
		bn.free();
		return bp;
	}

	@Override
	public void moveNext() {

		if (_blocks.size() == 0) {
			_isDone = true;
		}
	}

	private void searchForTreeBlocks(BlockNode bn) {

		Map<Block, IFactoryHarvestable> harvestables = MFRRegistry.getHarvestables();
		BlockNode cur;

		HarvestType type = getType(bn, harvestables);
		if (type == null || type == HarvestType.TreeFruit)
			return;

		SideOffset[] sides = !_harvestMode.isInverted ? SideOffset.ADJACENT_CUBE :
				SideOffset.ADJACENT_CUBE_INVERTED;

		for (SideOffset side : sides) {
			cur = BlockPool.getNext(
					bn.x + side.offsetX,
					bn.y + side.offsetY,
					bn.z + side.offsetZ
			);
			addIfValid(getType(cur, harvestables), cur);
		}
	}

	private void addIfValid(HarvestType type, BlockNode node) {

		if (type != null) {
			if (type == HarvestType.TreeFruit ||
					type == HarvestType.TreeLeaf) {
				_blocks.unshift(node);
				return;
			} else if (type == HarvestType.Tree) {
				_blocks.push(node);
				return;
			}
		}
		node.free();
	}

	private HarvestType getType(BlockNode bp, Map<Block, IFactoryHarvestable> harvestables) {

		Area area = _area;
		if (bp.x < area.xMin || bp.x > area.xMax ||
				bp.y < area.yMin || bp.y > area.yMax ||
				bp.z < area.zMin || bp.z > area.zMax)
			return null;

		BlockPos pos = this.pos.setPos(bp.x, bp.y, bp.z);
		if (!_world.isBlockLoaded(pos))
			return null;

		Block block = _world.getBlockState(pos).getBlock();
		if (harvestables.containsKey(block)) {
			IFactoryHarvestable h = harvestables.get(block);
			if (h.canBeHarvested(_world, _settings, pos)) {
				return h.getHarvestType();
			}
		}
		return null;
	}

	@Override
	public void reset(World world, Area treeArea, HarvestMode harvestMode, IFactorySettings settings) {

		setWorld(world);
		_harvestMode = harvestMode;
		_area = treeArea;
		free();
		_isDone = false;
		_blocks = new BlockPool();
		_blocks.push(BlockPool.getNext(treeArea.getOrigin()));
		_settings = settings;
	}

	@Override
	public void setWorld(World world) {

		_world = world;
	}

	@Override
	public boolean getIsDone() {

		return _isDone;
	}

	@Override
	public BlockPos getOrigin() {

		return _area.getOrigin();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {

		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("done", _isDone);
		data.setInteger("mode", _harvestMode.ordinal());
		BlockPos o = getOrigin();
		data.setIntArray("area", new int[] { o.getX() - _area.xMin, o.getY() - _area.yMin, _area.yMax - o.getY() });
		data.setIntArray("origin", new int[] { o.getX(), o.getY(), o.getZ() });
		NBTTagSmartByteArray list = new NBTTagSmartByteArray(_blocks.size() * 3 * 3);
		BlockNode bn = _blocks.poke();
		list.addVarInt(_blocks.size());
		while (bn != null) {
			list.addVarInt(bn.x).addVarInt(bn.y).addVarInt(bn.z);
			bn = bn.next;
		}
		data.setTag("curPos", list);
		tag.setTag("harvestManager", data);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		free();
		_blocks = new BlockPool();

		NBTTagCompound data = tag.getCompoundTag("harvestManager");
		_isDone = data.getBoolean("done");
		_harvestMode = HarvestMode.values()[data.getInteger("mode")];
		int[] area = data.getIntArray("area"), o = data.getIntArray("origin");
		if (o.length < 3 | area.length < 3) {
			_area = new Area(new BlockPos(0, -1, 0), 0, 0, 0);
			_isDone = true;
			return;
		}
		_area = new Area(new BlockPos(o[0], o[1], o[2]), area[0], area[1], area[2]);

		NBTBase baseList = data.getTag("curPos");
		byte[] blockCoords = ((NBTTagByteArray) baseList).getByteArray();
		for (int i = 0; i + 2 < blockCoords.length; i += 3) {
			_blocks.push(BlockPool.getNext(blockCoords[i], blockCoords[i + 1], blockCoords[i + 2]));
		}

		if (_blocks.size() == 0)
			_isDone = true;
	}

	@Override
	public void free() {

		if (_blocks != null)
			while (_blocks.poke() != null)
				_blocks.shift().free();
		_isDone = true;
	}
}
