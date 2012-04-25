/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * 
 * Copyright (C) 2012 Hervé Guillaume, Benjamin Sigg
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * Hervé Guillaume
 * rvguillaume@hotmail.com
 * FR - France
 *
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */

package bibliothek.gui.dock.toolbar.intern;

import bibliothek.gui.dock.ComponentDockable;
import bibliothek.gui.dock.action.DockActionSource;
import bibliothek.gui.dock.common.CStation;
import bibliothek.gui.dock.common.intern.CDockable;
import bibliothek.gui.dock.common.intern.CommonDockable;
import bibliothek.gui.dock.common.intern.station.CommonDockStation;
import bibliothek.gui.dock.toolbar.CToolbarItem;

/**
 * A {@link ComponentDockable} that is used as {@link CommonDockable} by a {@link CToolbarItem}.
 * @author Benjamin Sigg
 */
public class CommonComponentDockable extends ComponentDockable implements CommonDockable{
	private CToolbarItem item;
	
	/**
	 * Creates a new dockable.
	 * @param item the item which is represented by this dockable
	 */
	public CommonComponentDockable( CToolbarItem item ){
		this.item = item;
	}
	
	@Override
	public CommonComponentDockable asDockable(){
		return this;
	}
	
	@Override
	public CommonDockStation<?, ?> asDockStation(){
		return null;
	}
	
	@Override
	public CDockable getDockable(){
		return item;
	}

	@Override
	public CStation<?> getStation(){
		return null;
	}

	@Override
	public DockActionSource[] getSources(){
		return new DockActionSource[]{};
	}

}
