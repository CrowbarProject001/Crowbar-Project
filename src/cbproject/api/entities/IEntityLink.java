package cbproject.api.entities;

import net.minecraft.entity.Entity;

public interface IEntityLink<T extends Entity> {
	public T  getLinkedEntity();
	public void setLinkedEntity(T entity);
}
