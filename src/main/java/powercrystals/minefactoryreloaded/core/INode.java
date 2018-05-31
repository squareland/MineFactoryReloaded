package powercrystals.minefactoryreloaded.core;

public interface INode
{
	public boolean isNotValid();
	public void firstTick(IGridController grid);
	public void updateInternalTypes(IGridController grid);
}
