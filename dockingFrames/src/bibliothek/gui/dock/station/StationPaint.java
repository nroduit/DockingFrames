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


package bibliothek.gui.dock.station;

import java.awt.Graphics;
import java.awt.Rectangle;

import bibliothek.gui.dock.DockStation;
import bibliothek.gui.dock.Dockable;

/**
 * A StationPaint draws some parts of {@link DockStation}. All the default-stations
 * use a StationPaint to draw some markings when a {@link Dockable} is moved
 * or dragged.
 * @author Benjamin Sigg
 */
public interface StationPaint {
    /**
     * Paints a single line from x1/y1 to x2/y2.
     * @param g the graphics context used for painting
     * @param station the station on which to paint
     * @param x1 the x-coordinate of the first end of the line
     * @param y1 the y-coordinate of the first end of the line
     * @param x2 the x-coordinate of the second end of the line
     * @param y2 the y-coordinate of the second end of the line
     */
    public void drawInsertionLine( Graphics g, DockStation station, int x1, int y1, int x2, int y2 );
    
    /**
     * Draws a divider between two elements.
     * @param g the graphics context used for painting
     * @param station the station on which to paint
     * @param bounds the rectangle which marks the whole divider
     */
    public void drawDivider( Graphics g, DockStation station, Rectangle bounds );
    
    /**
     * Paints some markings when a {@link Dockable} is added to a {@link DockStation}.
     * @param g the graphics context used for painting
     * @param station the station on which to paint
     * @param stationBounds the area on the station which will be affected by the insertion
     * @param dockableBounds the bounds that the new child will have
     */
    public void drawInsertion( Graphics g, DockStation station, Rectangle stationBounds, Rectangle dockableBounds );
}
