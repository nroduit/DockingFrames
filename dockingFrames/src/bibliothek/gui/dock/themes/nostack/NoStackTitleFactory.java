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


package bibliothek.gui.dock.themes.nostack;

import bibliothek.gui.dock.DockStation;
import bibliothek.gui.dock.Dockable;
import bibliothek.gui.dock.station.FlapDockStation;
import bibliothek.gui.dock.station.ScreenDockStation;
import bibliothek.gui.dock.station.SplitDockStation;
import bibliothek.gui.dock.station.StackDockStation;
import bibliothek.gui.dock.title.DockTitle;
import bibliothek.gui.dock.title.DockTitleFactory;
import bibliothek.gui.dock.title.DockTitleVersion;

/**
 * A {@link DockTitleFactory} which does not create titles for 
 * {@link StackDockStation StackDockStations} but uses another 
 * factory as delegate to create titles for the other stations.
 */
public class NoStackTitleFactory implements DockTitleFactory{
    /** The delegate to create titles */
    private DockTitleFactory base;
    
    /**
     * Creates a new factory
     * @param base the delegate which will be used to create titles for other
     * stations than the {@link StackDockStation}
     */
    public NoStackTitleFactory( DockTitleFactory base ){
        if( base == null )
            throw new IllegalArgumentException( "Base must not be null" );
        
        this.base = base;
    }
    
    public DockTitle createDockableTitle( Dockable dockable, DockTitleVersion version ) {
        return base.createDockableTitle( dockable, version );
    }
    
    public <D extends Dockable & DockStation> DockTitle createStationTitle( D dockable, DockTitleVersion version ) {
        if( dockable instanceof StackDockStation ){
            String id = version.getID();
            if( id.equals( StackDockStation.TITLE_ID ) ||
                id.equals( FlapDockStation.WINDOW_TITLE_ID ) ||
                id.equals( ScreenDockStation.TITLE_ID ) ||
                id.equals( SplitDockStation.TITLE_ID ))
                
                return null;
        }
        
        return base.createStationTitle( dockable, version );
    }
}
