/*
 * @(#)DrawingPanel.java  1.0  11. March 2004
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors ("JHotDraw.org")
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JHotDraw.org ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * JHotDraw.org.
 */

package org.jhotdraw.samples.draw;

import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.undo.*;
import org.jhotdraw.util.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.jhotdraw.app.action.CopyAction;
import org.jhotdraw.app.action.CutAction;
import org.jhotdraw.app.action.DuplicateAction;
import org.jhotdraw.app.action.PasteAction;
import org.jhotdraw.app.action.SelectAllAction;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
/**
 * DrawingPanel.
 *
 *
 * @author Werner Randelshofer
 * @version 1.0 11. M�rz 2004  Created.
 */
public class DrawingPanel extends JPanel  {
    private UndoRedoManager undoManager;
    private Drawing drawing;
    private DrawingEditor editor;
    
    /** Creates new instance. */
    public DrawingPanel() {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        initComponents();
        undoManager = new UndoRedoManager();
        editor = new DefaultDrawingEditor();
        editor.add(view);
        
        addCreationButtonsTo(creationToolbar, editor);
        ToolBarButtonFactory.addAttributesButtonsTo(attributesToolbar, editor);
        
        JPopupButton pb = new JPopupButton();
        pb.setItemFont(UIManager.getFont("MenuItem.font"));
        labels.configureToolBarButton(pb, "actions");
        pb.add(new DuplicateAction());
        pb.addSeparator();
        pb.add(new GroupAction(editor));
        pb.add(new UngroupAction(editor));
        pb.addSeparator();
        pb.add(new MoveToFrontAction(editor));
        pb.add(new MoveToBackAction(editor));
        pb.addSeparator();
        pb.add(new CutAction());
        pb.add(new CopyAction());
        pb.add(new PasteAction());
        pb.add(new SelectAllAction());
        pb.add(new SelectSameAction(editor));
        pb.addSeparator();
        pb.add(undoManager.getUndoAction());
        pb.add(undoManager.getRedoAction());
        pb.addSeparator();
        pb.add(new ToggleGridAction(editor));
        
        JMenu m = new JMenu(labels.getString("zoom"));
        JRadioButtonMenuItem rbmi;
        ButtonGroup group = new ButtonGroup();
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.1, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.25, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.5, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.75, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 1.0, null)));
        rbmi.setSelected(true);
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 1.25, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 1.5, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 2, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 3, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 4, null)));
        group.add(rbmi);
        pb.add(m);
        pb.setFocusable(false);
        creationToolbar.addSeparator();
        creationToolbar.add(pb);
        
        
        DefaultDrawing drawing = new DefaultDrawing();
        view.setDrawing(drawing);
        drawing.addUndoableEditListener(undoManager);
    }
    
    public void setDrawing(Drawing d) {
        undoManager.discardAllEdits();
        view.getDrawing().removeUndoableEditListener(undoManager);
        view.setDrawing(d);
        d.addUndoableEditListener(undoManager);
    }
    public Drawing getDrawing() {
        return view.getDrawing();
    }
    public DrawingView getView() {
        return view;
    }
    public DrawingEditor getEditor() {
        return editor;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        toolButtonGroup = new javax.swing.ButtonGroup();
        scrollPane = new javax.swing.JScrollPane();
        view = new org.jhotdraw.draw.DefaultDrawingView();
        jPanel1 = new javax.swing.JPanel();
        creationToolbar = new javax.swing.JToolBar();
        attributesToolbar = new javax.swing.JToolBar();

        setLayout(new java.awt.BorderLayout());

        scrollPane.setViewportView(view);

        add(scrollPane, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        creationToolbar.setFloatable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(creationToolbar, gridBagConstraints);

        attributesToolbar.setFloatable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(attributesToolbar, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents

    private void addCreationButtonsTo(JToolBar tb, DrawingEditor editor) {
        addDefaultCreationButtonsTo(tb, editor, 
                ToolBarButtonFactory.createDrawingActions(editor), 
                ToolBarButtonFactory.createSelectionActions(editor)
                );
    }
    public void addDefaultCreationButtonsTo(JToolBar tb, final DrawingEditor editor,
            Collection<Action> drawingActions, Collection<Action> selectionActions) {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.draw.Labels");
        
        ToolBarButtonFactory.addSelectionToolTo(tb, editor, drawingActions, selectionActions);
        tb.addSeparator();
        
        AttributedFigure af;
        CreationTool ct;
        ConnectionTool cnt;
        ConnectionFigure lc;
        
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new RectangleFigure()), "createRectangle", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new RoundRectangleFigure()), "createRoundRectangle", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new EllipseFigure()), "createEllipse", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new DiamondFigure()), "createDiamond", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new TriangleFigure()), "createTriangle", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new CreationTool(new LineFigure()), "createLine", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, ct = new CreationTool(new LineFigure()), "createArrow", labels);
       af = (AttributedFigure) ct.getPrototype();
        af.setAttribute(END_DECORATION, new ArrowTip(0.35, 12, 11.3));
        ToolBarButtonFactory.addToolTo(tb, editor, new ConnectionTool(new LineConnectionFigure()), "createLineConnection", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, cnt = new ConnectionTool(new LineConnectionFigure()), "createElbowConnection", labels);
     lc =  cnt.getPrototype();
        lc.setLiner(new ElbowLiner());
        ToolBarButtonFactory.addToolTo(tb, editor, new BezierTool(new BezierFigure()), "createScribble", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new BezierTool(new BezierFigure(true)), "createPolygon", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new TextTool(new TextFigure()), "createText", labels);
        ToolBarButtonFactory.addToolTo(tb, editor, new TextAreaTool(new TextAreaFigure()), "createTextArea", labels);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar attributesToolbar;
    private javax.swing.JToolBar creationToolbar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.ButtonGroup toolButtonGroup;
    private org.jhotdraw.draw.DefaultDrawingView view;
    // End of variables declaration//GEN-END:variables
    
}