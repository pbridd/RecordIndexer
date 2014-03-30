package client.gui.synchronization;

import shared.model.User;

public interface WindowManagerListener {
	public void visibilityToggled(boolean loginWindowVisible, boolean mainWindowVisible, User currUser);
}
