package name.abuchen.portfolio.ui.theme;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.dom.properties.converters.ICSSValueConverter;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.ILegend;
import org.eclipse.swtchart.ITitle;
import org.w3c.dom.css.CSSValue;

import name.abuchen.portfolio.ui.util.chart.ScatterChart;

@SuppressWarnings("restriction")
public class ChartCSSHandler extends AbstractCSSPropertySWTHandler implements ICSSPropertyHandler
{
    private static final String BACKGROUND_COLOR = "background-color"; //$NON-NLS-1$
    private static final String AXIS_COLOR = "axis-color"; //$NON-NLS-1$
    private static final String GRID_COLOR = "grid-color"; //$NON-NLS-1$
    private static final String HIGHLIGHT_COLOR = "highlight-color"; //$NON-NLS-1$

    @Override
    protected void applyCSSProperty(Control control, String property, CSSValue value, String pseudo, CSSEngine engine)
                    throws Exception
    {
        if (!(control instanceof Chart))
            return;

        Chart chart = (Chart) control;
        if (AXIS_COLOR.equalsIgnoreCase(property) && (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE))
        {
            Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());

            for (IAxis axis : chart.getAxisSet().getAxes())
            {
                axis.getTitle().setForeground(newColor);
                axis.getTick().setForeground(newColor);
            }

            ITitle title = chart.getTitle();
            if (title != null)
                title.setForeground(newColor);

            ILegend legend = chart.getLegend();
            if (legend != null)
                legend.setForeground(newColor);
        }
        else if (GRID_COLOR.equalsIgnoreCase(property) && (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE))
        {
            Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());

            for (IAxis axis : chart.getAxisSet().getAxes())
                axis.getGrid().setForeground(newColor);
        }
        else if (chart instanceof ScatterChart && HIGHLIGHT_COLOR.equalsIgnoreCase(property)
                        && (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE))
        {
            Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
            ((ScatterChart) chart).setHighlightColor(newColor);
        }
        else if (BACKGROUND_COLOR.equalsIgnoreCase(property)
                        && (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE))
        {
            Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());

            chart.setBackground(newColor);

            Control plotArea = chart.getPlotArea().getControl();
            if (plotArea != null)
                plotArea.setBackground(newColor);
            ILegend legend = chart.getLegend();
            if (legend != null)
                legend.setBackground(newColor);
        }
    }

    @Override
    protected String retrieveCSSProperty(Control control, String property, String pseudo, CSSEngine engine)
                    throws Exception
    {
        if (!(control instanceof Chart))
            return null;

        Chart chart = (Chart) control;
        if (AXIS_COLOR.equalsIgnoreCase(property))
        {
            ICSSValueConverter cssValueConverter = engine.getCSSValueConverter(String.class);
            return cssValueConverter.convert(chart.getTitle().getForeground(), engine, null);
        }
        else if (GRID_COLOR.equalsIgnoreCase(property))
        {
            ICSSValueConverter cssValueConverter = engine.getCSSValueConverter(String.class);
            return cssValueConverter.convert(chart.getAxisSet().getAxes()[0].getGrid().getForeground(), engine, null);
        }
        else if (HIGHLIGHT_COLOR.equalsIgnoreCase(property) && chart instanceof ScatterChart)
        {
            ICSSValueConverter cssValueConverter = engine.getCSSValueConverter(String.class);
            return cssValueConverter.convert(((ScatterChart) chart).getHighlightColor(), engine, null);
        }
        else if (BACKGROUND_COLOR.equalsIgnoreCase(property))
        {
            ICSSValueConverter cssValueConverter = engine.getCSSValueConverter(String.class);
            return cssValueConverter.convert(chart.getPlotArea().getBackground(), engine, null);
        }

        return null;
    }

}
