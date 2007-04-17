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


package bibliothek.gui;

import java.util.Hashtable;
import java.util.Map;

import bibliothek.gui.dock.DockStation;
import bibliothek.gui.dock.title.DockTitleFactory;
import bibliothek.gui.dock.title.DockTitleVersion;

/**
 * The manager of the {@link DockTitleFactory DockTitleFactories}. Every 
 * {@link DockStation} will try to register some factories here. The factories
 * can be overridden by a client or a {@link DockTheme}.
 * @author Benjamin Sigg
 */
public class DockTitleManager {
    /** A map of all versions registered at this manager */
	private Map<String, DockTitleVersion> titleVersions = new Hashtable<String, DockTitleVersion>();
    /** The controller for which the factories are stored */
    private DockController controller;
    
    /**
     * Creates a new manager
     * @param controller the controller for which the titles are used
     */
    public DockTitleManager( DockController controller ){
    	if( controller == null )
    		throw new IllegalArgumentException( "Controller must not be null" );
    	this.controller = controller;
    }
    
    /**
     * Tests whether there is a handle registered at <code>id</code> or not.
     * @param id the id
     * @return <code>true</code> if there is a handle, <code>false</code>
     * otherwise
     */
    public boolean existsTitleVersion( String id ){
        return titleVersions.containsKey( id );
    }
    
    /**
     * Gets the handle with the key <code>id</code>.
     * @param id the key
     * @return the handle or <code>null</code> if no handle is
     * registered
     */
    public DockTitleVersion getVersion( String id ){
        return titleVersions.get( id );
    }
    
    /**
     * Registers a factory with client-priority
     * @param id the key of the factory
     * @param factory the factory
     * @return a handle of the factories of this id
     */
    public DockTitleVersion registerClient( String id, DockTitleFactory factory ){
    	return register( id, factory, DockTitleVersion.Priority.CLIENT );
    }

    /**
     * Registers a factory with theme-priority
     * @param id the key of the factory
     * @param factory the factory
     * @return a handle of the factories of this id
     */
    public DockTitleVersion registerTheme( String id, DockTitleFactory factory ){
    	return register( id, factory, DockTitleVersion.Priority.THEME );
    }
    
    /**
     * Registers a factory with default-priority
     * @param id the key of the factory
     * @param factory the factory
     * @return a handle of the factories of this id
     */
    public DockTitleVersion registerDefault( String id, DockTitleFactory factory ){
    	return register( id, factory, DockTitleVersion.Priority.DEFAULT );
    }
    
    /**
     * Registers a factory at the given key
     * @param id the key of the factory
     * @param factory the factory
     * @param priority the priority of this registration
     * @return the handle to the factory or a factory with higher priority
     */
    public DockTitleVersion register( String id, DockTitleFactory factory, DockTitleVersion.Priority priority ){
        DockTitleVersion version = titleVersions.get( id );
        if( version == null ){
            version = new DockTitleVersion( controller, id );
            titleVersions.put( id, version );
        }
        
        version.setFactory( factory, priority );
        
        return version;    
    }
    
    /**
     * Removes all factories that were added by a theme
     */
    public void clearThemeFactories(){
    	for( DockTitleVersion version : titleVersions.values() )
    		version.setFactory( null, DockTitleVersion.Priority.THEME );
    }
}
