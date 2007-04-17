/**
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * 
 * Copyright (C) 2007 Benjamin Sigg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * 
 * Wunderklingerstr. 59
 * 8215 Hallau
 * CH - Switzerland
 */


package bibliothek.gui.dock;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.event.MouseInputListener;

import bibliothek.gui.DockController;
import bibliothek.gui.dock.action.DockAction;
import bibliothek.gui.dock.action.DockActionSource;
import bibliothek.gui.dock.event.DockActionSourceListener;
import bibliothek.gui.dock.event.DockableListener;
import bibliothek.gui.dock.title.DockTitle;
import bibliothek.gui.dock.title.DockTitleVersion;

/**
 * A Dockable is a window which is put onto a {@link DockStation}. The user
 * can grab a Dockable and drag it to another station.<br>
 * A Dockable has some properties:
 * <ul>
 *  <li>An icon which is displayed on the title</li>
 *  <li>A title-text which is displayed on the title</li>
 *  <li>A {@link DockStation} which is the parent of the Dockable</li>
 *  <li>A {@link DockController} which is responsible to allow the user to
 *  drag the Dockable.</li>
 *  <li>A {@link Component} which represents the Dockable</li>
 *  <li>A {@link DockActionSource} which provides some {@link DockAction DockActions}. 
 *  Each of the action can be triggered by the user, and can execute any it likes.</li>
 * </ul>
 * 
 * @author Benjamin Sigg
 */
public interface Dockable extends DockElement{
    /**
     * Sets the parent property. This Dockable is shown as direct child of
     * <code>station</code>.
     * @param station the parent, may be <code>null</code> if this 
     * Dockable is not visible at all.
     */
    public void setDockParent( DockStation station );
    
    /**
     * Gets the current parent, which is the last argument of {@link #setDockParent(DockStation)}.
     * @return the parent property, can be <code>null</code>
     */
    public DockStation getDockParent();
    
    /**
     * Sets the controller in whose realm this Dockable is. A value of <code>null</code>
     * means that this {@link Dockable} is not managed by a controller.
     * @param controller the owner, may be <code>null</code>
     */
    public void setController( DockController controller );
    
    /**
     * Gets the current controller, the argument of the last call of
     * {@link #setController(DockController)}.
     * @return the controller, can be <code>null</code>
     */
    public DockController getController();
    
    /**
     * Adds a listener to this Dockable. The listener has to be informed if
     * a property of this Dockable changes.
     * @param listener the new listener
     */
    public void addDockableListener( DockableListener listener );
    
    /**
     * Removes a listener from this Dockable.
     * @param listener the listener to remove
     */
    public void removeDockableListener( DockableListener listener );
    
    /**
     * Adds a {@link MouseInputListener} to the component of this Dockable.
     * A Dockable has to decide by itself which {@link Component Components}
     * should be observer, but generally all free areas should be covered.
     * It's also possible just to ignore the listener, but that's not the
     * preferred behavior.
     * @param listener the mouse listener
     */
    public void addMouseInputListener( MouseInputListener listener );
    
    /**
     * Removes a listener that was earlier added to this Dockable. 
     * @param listener The listener to remove
     */
    public void removeMouseInputListener( MouseInputListener listener );
    
    /**
     * Tells whether <code>station</code> is an accepted parent for this 
     * Dockable or not. The user is not able to drag a Dockable to a station
     * which is not accepted.
     * @param station a possible parent
     * @return whether <code>station</code> could be a parent or not
     */
    public boolean accept( DockStation station );
    
    /**
     * Tells whether <code>base</code> could be the parent of a combination
     * between this Dockable and <code>neighbor</code>. The user is not able
     * to make a combination between this Dockable and <code>neighbor</code>
     * if this method does not accept the operation.
     * @param base the future parent of the combination
     * @param neighbor a Dockable whose parent will be the same parent as
     * the parent of this Dockable
     * @return <code>true</code> if the combination is allowed, <code>false</code>
     * otherwise
     */
    public boolean accept( DockStation base, Dockable neighbor );
    
    /**
     * Gets the {@link Component} which represents this Dockable. Note that
     * the component should be a 
     * {@link java.awt.Container#setFocusCycleRoot(boolean) focus cycle root}
     * @return the visible representation
     */
    public Component getComponent();
    
    /**
     * Gest the current title-text of this Dockable.
     * @return the text
     */
    public String getTitleText();
    
    /**
     * Gets the current icon of this Dockable.
     * @return the icon, may be <code>null</code>
     */
    public Icon getTitleIcon();
    
    /**
     * Invoked to get a graphical representation of a title for this Dockable.<br>
     * There are several requirements to the title and the caller:
     * <ul>
     *  <li>The {@link DockTitle#getDockable() owner} of the title must be this Dockable.</li>
     *  <li>The {@link DockTitle#getOrigin() origin} of the title must be <code>version</code>.</li>
     *  <li>The title must <b>not</b> be binded</li>
     *  <li>The result should be independent of the current state of this Dockable.</li>
     *  <li>The method should not change any attribute of this Dockable</li>
     *  <li>The client must call the {@link #bind(DockTitle)}-method of this Dockable
     *  before using the title. Note that a client <b>must not</b> call the
     *  bind-method of DockTitle</li>
     *  <li>The client must call the {@link #unbind(DockTitle)}-method when he no
     *  longer needs the title. Note that the client <b>must not</b> call the
     *  unbind-method of the DockTitle</li>
     * </ul>
     * @param version which title is required. If this Dockable does not have
     * a special rule for the given version, it can return the result of
     * {@link DockTitleVersion#createDockable(Dockable)}.
     * @return The title, can be <code>null</code> if no title should be shown.
     * Note that not all clients can handle a <code>null</code>-title, if in
     * doubt, return a title.
     */
    public DockTitle getDockTitle( DockTitleVersion version );
    
    /**
     * Called by clients which want to show a title of this Dockable. If the
     * client is a DockStation, {@link DockTitle#bind()} will be called
     * automatically by the controller, other clients have to call
     * {@link DockTitle#bind()} manually.<br>
     * This method must at least inform all listeners, that <code>title</code>
     * was binded. However, the method {@link DockTitle#bind()} must not
     * be invoked by this method.
     * @param title the title which will be show some things of this Dockable
     * @see #unbind(DockTitle)
     */
    public void bind( DockTitle title );
    
    /**
     * Clients should call this method if a {@link DockTitle} is no longer
     * needed. In case that the client is a {@link DockStation}, then the
     * controller will call {@link DockTitle#unbind()} at an appropriate
     * time. Other clients must call {@link DockTitle#unbind()} itself.<br>
     * This method must inform all listeners that <code>title</code>
     * is no longer binded. However, this method must not call
     * {@link DockTitle#unbind()}.
     * @param title the title which will be no longer connected to this
     * Dockable
     * @see #bind(DockTitle)
     */
    public void unbind( DockTitle title );
    
    /**
     * Gets a list of {@link DockAction} which should be triggerable if
     * this Dockable is visible. The list can be modified by this Dockable
     * at every time, clients have to react on these changes by adding
     * a {@link DockActionSourceListener} to the result.
     * @return the source of actions, can be <code>null</code> if no actions
     * are available
     */
    public DockActionSource getActionOffers();
}
