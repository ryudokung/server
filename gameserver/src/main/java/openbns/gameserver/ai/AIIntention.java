package openbns.gameserver.ai;

/**
 * Created by: Eugene Chipachenko
 * Date: 01.03.14
 */
public enum AIIntention
{
  /**
   * Do nothing, disconnect AI of NPC if no players around
   */
  AI_INTENTION_IDLE,
  /**
   * Alerted state without goal : scan attackable targets, random walk, etc
   */
  AI_INTENTION_ACTIVE,
  /**
   * Attack target (cast combat magic, go to target, combat), may be ignored,
   * if target is locked on another character or a peacefull zone and so on
   */
  AI_INTENTION_ATTACK,
  /**
   * Cast a spell, depending on the spell - may start or stop attacking
   */
  AI_INTENTION_CAST,
  /**
   * PickUp and item, (got to item, pickup it, become idle
   */
  AI_INTENTION_PICK_UP,
  /**
   * Move to target, then interact
   */
  AI_INTENTION_INTERACT,
  /**
   * Follow to target
   */
  AI_INTENTION_FOLLOW
}
