/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.unit.common;

import static org.eclipse.sirius.ui.tools.api.color.VisualBindingManager.clamp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.sirius.ui.tools.api.color.VisualBindingManager;
import org.eclipse.sirius.viewpoint.FontFormat;
import org.eclipse.sirius.viewpoint.description.DescriptionFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.eclipse.sirius.viewpoint.description.SystemColors;
import org.eclipse.sirius.viewpoint.description.UserFixedColor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import junit.framework.TestCase;

/**
 * Visual binding manager test.
 * 
 * @author fmorel, pcdavid
 */
public class VisualBindingManagerTestCase extends TestCase {
    private VisualBindingManager visualManager = new VisualBindingManager();

    @Override
    protected void setUp() throws Exception {
        visualManager.init(5, 5);
    }

    @Override
    protected void tearDown() throws Exception {
        visualManager.dispose();
    }

    /**
     * This test assumes that the local version of nameToRGB is in sync with the
     * real value in {@link VisualBindingManager}.
     */
    public void testSystemPaletteIncludesExactlyTheDefaultColors() {
        Map<String, RGB> systemPalette = visualManager.getSystemPalette();
        SystemColors[] allDefaultColors = SystemColors.values();
        for (SystemColors dc : allDefaultColors) {
            assertTrue("DefaultColor " + dc.getName() + " missing from the system palette", systemPalette.containsKey(dc.getName()));
        }
        assertFalse("System palette contains colors not in SystemColors", systemPalette.size() > allDefaultColors.length);
    }

    /**
     * Tests the new conversion method using color-space distances.
     */
    public void testDefaultColorConvertsToSelfUsingColorspaceDistances() {
        for (SystemColors defaultColor : SystemColors.values()) {
            FixedColor color = createEquivalentFixedColor(defaultColor);
            SystemColors converted = visualManager.findClosestSystemColor(color);
            assertEquals(defaultColor, converted);
        }
    }

    private FixedColor createEquivalentFixedColor(SystemColors defaultColor) {
        UserFixedColor color = DescriptionFactory.eINSTANCE.createUserFixedColor();
        RGB rgb = visualManager.getSystemPalette().get(defaultColor.getName().toLowerCase());
        color.setName("<anonymous>");
        color.setRed(rgb.red);
        color.setGreen(rgb.green);
        color.setBlue(rgb.blue);
        return color;
    }

    public void testColorCache() {
        // WARNING: We use the named colors to test the cache instead of
        // #getColorFromValue(int), as that method internally uses named colors
        // "red" and "green", which have side-effects on the cache.

        final Set<String> names = new HashSet<String>(visualManager.getSystemPalette().keySet());
        final Color[] colors = new Color[names.size()];
        final int N = 3;
        final int cacheSize = names.size() - N;
        VisualBindingManager vbm = new VisualBindingManager();
        vbm.init(cacheSize, cacheSize);

        // Fill the cache.
        int i = 0;
        for (String name : names) {
            Color color = vbm.getColorFromName(name);
            colors[i++] = color;
            assertSame("Color " + name + " should be cached right after it is retrieved", color, vbm.getColorFromName(name));
        }

        // Re-fill the cache with the same values.
        i = 0;
        for (String name : names) {
            Color color = vbm.getColorFromName(name);
            colors[i++] = color;
            assertSame("Color " + name + " should be cached right after it is retrieved", color, vbm.getColorFromName(name));
        }

        // Check retrieval of the values we put in. The first N should be
        // disposed; the last cacheSize should still be cached.
        i = 0;
        for (String name : names) {
            Color color = colors[i];
            if (i < N) {
                assertTrue("Color " + name + " should have been evicted from the cache and disposed", color.isDisposed());
            } else {
                assertFalse("Color " + name + " should not have been disposed", color.isDisposed());
                assertSame("Color " + name + " should still be in cache", color, vbm.getColorFromName(name));
            }
            i += 1;
        }
    }

    public void testClamp() {
        assertEquals(0, clamp(0, 0, 0));
        assertEquals(0, clamp(-100, 0, 255));
        assertEquals(255, clamp(300, 0, 255));
        assertEquals(100, clamp(100, 99, 100));
        assertEquals(99, clamp(99, 99, 100));
        assertEquals(-1, clamp(-1, -1, 0));
    }

    /**
     * Tests
     * {@link VisualBindingManager#getDefaultFontWithDefaultSizeAndGivenLabelFormat(List)}
     * . The created font should have the runtime height and the given format.
     */
    public void testGetFontFromLabelFormatAndSize() {
        List<FontFormat> labelFormat = new ArrayList<FontFormat>();
        labelFormat.add(FontFormat.get(FontFormat.BOLD));
        Font fontFromLabelFormatAndSize = visualManager.getDefaultFontWithWorkbenchDefaultSizeAndFromLabelFormat(labelFormat);
        FontData fontData = fontFromLabelFormatAndSize.getFontData()[0];
        FontData[] systemFontData = Display.getDefault().getSystemFont().getFontData();
        assertEquals("the font height is not the runtime one.", systemFontData[0].getHeight(), fontData.getHeight());
        assertEquals("the font style is not the specified one.", FontFormat.BOLD, fontData.getStyle());
        assertEquals("the font name is not the default one.", systemFontData[0].getName(), fontData.getName());
    }
}
