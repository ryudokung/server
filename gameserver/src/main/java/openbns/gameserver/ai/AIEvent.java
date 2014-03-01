package openbns.gameserver.ai;

/**
 * Created by: Eugene Chipachenko
 * Date: 01.03.14
 */
public enum AIEvent
{
  /**
   * Something has changed, usually a previous step has being completed
   * or maybe was completed, the AI must thing on next action
   */
  THINK,
  /**
   * The actor was attacked. This event comes each time a physical or magical
   * attack was done on the actor. NPC may start attack in responce, or ignore
   * this event if they already attack someone, or change target and so on.
   */
  ATTACKED,
  /**
   * The actor arrived to assigned location, or it's a time to modify
   * movement destination (follow, interact, random move and others intentions).
   */
  ARRIVED,
  DEAD,
  SPAWN,
  DESPAWN
}
