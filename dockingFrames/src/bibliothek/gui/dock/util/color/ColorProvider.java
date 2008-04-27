/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * 
 * Copyright (C) 2007 Benjamin Sigg
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
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */
package bibliothek.gui.dock.util.color;

import java.awt.Color;

import bibliothek.gui.dock.util.UIBridge;

/**
 * A <code>ColorProvider</code> is a layer between a {@link ColorManager} and
 * a {@link DockColor}. A provider can exchange the color that is used for
 * some <code>DockColor</code>.
 * @author Benjamin Sigg
 * @param <D> the type of {@link DockColor}s this provider can handle
 * @deprecated please use {@link UIBridge UIBridge<Color, D extends DockColor>} instead.
 */
@Deprecated
public interface ColorProvider<D extends DockColor> extends UIBridge<Color, D> {
    // no new methods
}
