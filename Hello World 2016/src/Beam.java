import org.joml.Vector4d;

public class Beam extends Block
{
	private boolean toRight;
	private Player source;
	private Player target;

	public Beam(World world, Player s, Player t)
	{
		super(world, s.getPosX(), s.getPosY(), t.getPosX(), t.getPosY());

		source = s;
		target = t;

		pos.x += s.getSize() / 2;
		pos.y += s.getSize() / 2;
		//pos.z += t.getSize() / 2;
		//pos.w += t.getSize() / 2;
		pos.w = pos.y;

		if(s.getPosX() > t.getPosX())
			toRight = false;
		else
			toRight = true;

		pos.z = toRight ? world.getWidth() : 0;
	}

	public boolean trace()
	{
		Block block;

		for(int i = 0; i < worldObj.getBlocks().size(); i++)
		{
			block = worldObj.getBlocks().get(i);

			if(CollisionHelper.colliding(pos, block.pos))
			{
				pos.z = toRight ? block.pos.x : block.pos.z;
			}
		}
		
		if(CollisionHelper.colliding(pos, target.pos))
			return true;
		
		return false;
	}

}
