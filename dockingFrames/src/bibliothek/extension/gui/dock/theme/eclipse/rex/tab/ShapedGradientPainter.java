package bibliothek.extension.gui.dock.theme.eclipse.rex.tab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import bibliothek.extension.gui.dock.theme.eclipse.rex.RexSystemColor;
import bibliothek.extension.gui.dock.theme.eclipse.rex.RexTabbedComponent;


/**
 * @author Janni Kovacs
 */
public class ShapedGradientPainter extends JComponent implements TabComponent {
	public static final TabPainter FACTORY = new TabPainter(){
		public TabComponent createTabComponent( RexTabbedComponent component, Tab tab, int index ){
			return new ShapedGradientPainter( component, tab, index );
		}

		public void paintTabStrip( RexTabbedComponent tabbedComponent,
		        Component tabStrip, Graphics g ) {
		
			int selectedIndex = tabbedComponent.getSelectedIndex();
			if (selectedIndex != -1) {
				Rectangle selectedBounds = tabbedComponent.getBoundsAt(selectedIndex);
				int to = selectedBounds.x;
				int from = selectedBounds.x + selectedBounds.width;
				int end = tabStrip.getWidth();
				Color lineColor = SystemColor.controlShadow;
				g.setColor(lineColor);
				int y = tabStrip.getHeight()-1;
				
				if (to != 0)
					g.drawLine(0, y, to, y);
				if( from != end )
					g.drawLine(from, y, end, y);
			}
		}
	};
	
	private boolean paintIconWhenInactive = false;

	private boolean hasFocus;
	private boolean isSelected;
	private RexTabbedComponent comp;
	private Tab tab;
	private int tabIndex;
	
	private MatteBorder contentBorder = new MatteBorder(2, 2, 2, 2, Color.BLACK);
	
	public ShapedGradientPainter( RexTabbedComponent component, Tab tab, int index ){
		setLayout( null );
		setOpaque( false );
		this.comp = component;
		this.tab = tab;
		this.tabIndex = index;
		if( tab.getTabComponent() != null )
			add( tab.getTabComponent() );
		
		addHierarchyListener( new WindowActiveObserver() );
	}
	
	public Component getComponent(){
		return this;
	}
	
	public void setFocused( boolean focused ){
		hasFocus = focused;
		updateBorder();
		repaint();
	}
	
	public void setSelected( boolean selected ){
		isSelected = selected;
		updateBorder();
		revalidate();
	}
	
	public void setIndex( int index ){
		tabIndex = index;
		repaint();
	}
	
	private void updateBorder(){
		Color color2 = RexSystemColor.getActiveTitleColorGradient();
		
		Window window = SwingUtilities.getWindowAncestor(comp);
		boolean focusTemporarilyLost = false;
		
		if( window != null ){
			focusTemporarilyLost = !window.isActive();
		}
		
		if (hasFocus && focusTemporarilyLost) {
			color2 = RexSystemColor.getInactiveTitleColor();
		} else if (!hasFocus) {
			color2 = UIManager.getColor("Panel.background");
		}
		
		// set border around tab content
		if (!color2.equals(contentBorder.getMatteColor())) {
			contentBorder = new MatteBorder(2, 2, 2, 2, color2);
			if( comp != null )
				comp.updateContentBorder();
		}
	}

	public Border getContentBorder() {
		return contentBorder;
	}

	@Override
	public Dimension getPreferredSize() {
		FontRenderContext frc = new FontRenderContext(null, false, false);
		Rectangle2D bounds = UIManager.getFont("Label.font").getStringBounds(tab.getTitle(), frc);
		int width = 5 + (int) bounds.getWidth() + 5;
		int height = 23;
		if ((paintIconWhenInactive || isSelected) && tab.getIcon() != null)
			width += tab.getIcon().getIconWidth() + 5;
		if (isSelected)
			width += 35;
		
		if( tab.getTabComponent() != null ){
			Dimension tabPreferred = tab.getTabComponent().getPreferredSize();
			width += tabPreferred.width;
			height = Math.max( height, tabPreferred.height );
		}
		
		return new Dimension(width, height);
	}

	@Override
	public void doLayout(){
		if( tab.getTabComponent() != null ){
			FontRenderContext frc = new FontRenderContext(null, false, false);
			Rectangle2D bounds = UIManager.getFont("Label.font").getStringBounds(tab.getTitle(), frc);
			int x = 5 + (int) bounds.getWidth() + 5;
			if ((paintIconWhenInactive || isSelected) && tab.getIcon() != null)
				x += tab.getIcon().getIconWidth() + 5;
			
			if( isSelected )
				x += 5;
			
			Dimension preferred = tab.getTabComponent().getPreferredSize();
			int width = Math.min( preferred.width, getWidth()-x );
			
			tab.getTabComponent().setBounds( x, 0, width, getHeight() );
		}
	}
	
	public void update(){
		revalidate();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());//getBounds();
		int x = 0, y = 0;
		int w = bounds.width, h = bounds.height;
		Graphics2D g2d = (Graphics2D) g;
		Color lineColor = SystemColor.controlShadow;

//		GradientPaint selectedGradient = new GradientPaint(x, y, SystemColor.activeCaption, x, y + height,
//				SystemColor.activeCaption.darker());
		// Gradient for selected tab
		Color color1 = RexSystemColor.getActiveTitleColor(), color2 = RexSystemColor.getActiveTitleColorGradient();
		
		Window window = SwingUtilities.getWindowAncestor(comp);
		boolean focusTemporarilyLost = false;
		
		if( window != null ){
			focusTemporarilyLost = !window.isActive();
		}
		
		if (hasFocus && focusTemporarilyLost) {
			color2 = RexSystemColor.getInactiveTitleColor();
			color1 = color2;
		} else if (!hasFocus) {
			color1 = Color.WHITE;
			color2 = UIManager.getColor("Panel.background");
		}
		GradientPaint selectedGradient = new GradientPaint(x, y, color1, x, y + h, color2);

		// draw tab if selected
		Paint old = g2d.getPaint();
		if (isSelected) {
			// draw line at the bottom
			g.setColor(lineColor);
			//	Polygon outer = extendPolygon(xpoints, ypoints, 5);
			//		Polygon inner = new Polygon(xpoints, ypoints, xpoints.length);
			final int[] TOP_LEFT_CORNER = new int[]{0, 6, 1, 5, 1, 4, 4, 1, 5, 1, 6, 0};
			int tabHeight = 24;
			int d = tabHeight - 12;
			int[] curve = new int[]{0, 0, 0, 1, 2, 1, 3, 2, 5, 2, 6, 3, 7, 3, 9, 5, 10, 5,
					11, 6, 11 + d, 6 + d,
					12 + d, 7 + d, 13 + d, 7 + d, 15 + d, 9 + d, 16 + d, 9 + d, 17 + d, 10 + d, 19 + d, 10 + d,
					20 + d,
					11 + d, 22 + d, 11 + d, 23 + d, 12 + d};
			int rightEdge = Math.min(x + w - 20, comp.getWidth()); // can be replaced by: x + w - 20
			int curveWidth = 26 + d;
			int curveIndent = curveWidth / 3;
			int[] left = TOP_LEFT_CORNER;
			int[] right = curve;
			int[] shape = new int[left.length + right.length + 8];
			int index = 0;
			int height = 23;
			shape[index++] = x; // first point repeated here because below we reuse shape to draw outline
			shape[index++] = y + height + 1;
			shape[index++] = x;
			shape[index++] = y + height + 1;
			for (int i = 0; i < left.length / 2; i++) {
				shape[index++] = x + left[2 * i];
				shape[index++] = y + left[2 * i + 1];
			}
			for (int i = 0; i < right.length / 2; i++) {
				shape[index++] = rightEdge - curveIndent + right[2 * i];
				shape[index++] = y + right[2 * i + 1];
			}
			shape[index++] = rightEdge + curveWidth - curveIndent;
			shape[index++] = y + height + 1;
			shape[index++] = rightEdge + curveWidth - curveIndent;
			shape[index++] = y + height + 1;
			Polygon inner = makePolygon(shape);
			Polygon outer = copyPolygon(inner);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// draw outline from 0/0 or -1/0 resp.
			if (tabIndex == 0)
				outer.translate(-1, 0);
			g.fillPolygon(outer);
			// draw outline from 2/0
			outer.translate(2, 0);
			g.fillPolygon(outer);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g2d.setPaint(selectedGradient);
			// draw inner gradient from 1/0 or 0/0 resp.
			if (tabIndex != 0)
				inner.translate(1, 0);
			g.fillPolygon(inner);
		}
		g2d.setPaint(old);

		// draw icon
		int iconOffset = 0;
		if (isSelected || paintIconWhenInactive) {
			Icon i = tab.getIcon();
			if (i != null) {
				i.paintIcon(comp, g, 5, 4);
				iconOffset = i.getIconWidth() + 5;
			}
		}

		// draw close label

		// draw separator lines
		if (!isSelected && tabIndex != comp.indexOf(comp.getSelectedTab()) - 1) {
			g.setColor(lineColor);
			g.drawLine(w - 1, 0, w - 1, h);
		}

		// draw text
		g.setColor(isSelected && hasFocus ? SystemColor.activeCaptionText : SystemColor.controlText);
		g.drawString(tab.getTitle(), x + 5 + iconOffset, h / 2 + g.getFontMetrics().getHeight() / 2 - 2);
	}

	private Polygon copyPolygon(Polygon p) {
		int[] xpoints = new int[p.npoints];
		int[] ypoints = new int[p.npoints];
		System.arraycopy(p.xpoints, 0, xpoints, 0, xpoints.length);
		System.arraycopy(p.ypoints, 0, ypoints, 0, ypoints.length);
		return new Polygon(xpoints, ypoints, xpoints.length);
	}

	private Polygon makePolygon(int[] shape) {
		int[] xpoints = new int[shape.length / 2];
		int[] ypoints = new int[shape.length / 2];
		for (int i = 0, j = 0; i < shape.length - 1; i += 2, j++) {
			int x = shape[i];
			int y = shape[i + 1];
			xpoints[j] = x;
			ypoints[j] = y;
		}
		return new Polygon(xpoints, ypoints, xpoints.length);
	}

	public boolean doPaintIconWhenInactive() {
		return paintIconWhenInactive;
	}

	public void setPaintIconWhenInactive(boolean paintIconWhenInactive) {
		this.paintIconWhenInactive = paintIconWhenInactive;
		revalidate();
		repaint();
	}
	
	private class WindowActiveObserver extends WindowAdapter implements HierarchyListener{
		private Window window;
		
		public void hierarchyChanged( HierarchyEvent e ){
			if( window != null ){
				window.removeWindowListener( this );
				window = null;
			}
			
			window = SwingUtilities.getWindowAncestor( ShapedGradientPainter.this );
			
			if( window != null ){
				window.addWindowListener( this );
				updateBorder();
				repaint();
			}
		}
		
		@Override
		public void windowActivated( WindowEvent e ){
			updateBorder();
			repaint();
		}
		
		@Override
		public void windowDeactivated( WindowEvent e ){
			updateBorder();
			repaint();
		}
	}
}
