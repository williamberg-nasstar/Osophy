package gui;

import io.Persistence;
import io.PersistenceException;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import model.Basis;

public class DefaultBasisEditor {

  private static final Color BG_COLOR = Color.LIGHT_GRAY;

  private Persistence store;

  private Basis basis;

  private JTextField loadFileField;
  private JButton zoomInButton;
  private JButton zoomOutButton;
  private JButton loadButton;
  private JButton saveButton;
  private JLabel errorText;

  private volatile int zoomLevel;

  public DefaultBasisEditor(Basis basis) {
    this.basis = basis;

    JFrame top = new JFrame("Osophy");
    top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    top.setResizable(true);

    LayoutManager gbLayout = new GridBagLayout();
    top.setLayout(gbLayout);

    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    top.setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout(0, 0));

    contentPane.add(new GraphPanel(), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();

    zoomInButton = new JButton("Zoom in +");
    zoomInButton.addMouseListener(new DefaultMouseListener());
    buttonPanel.add(zoomInButton);

    zoomOutButton = new JButton("Zoom out -");
    zoomOutButton.addMouseListener(new DefaultMouseListener());
    buttonPanel.add(zoomOutButton);

    saveButton = new JButton("Save");
    saveButton.addMouseListener(new DefaultMouseListener());
    buttonPanel.add(saveButton);

    loadButton = new JButton("Load");
    loadButton.addMouseListener(new DefaultMouseListener());
    buttonPanel.add(loadButton);

    loadFileField = new JTextField();
    loadFileField.setPreferredSize(new Dimension(100, (int) loadButton.getPreferredSize().getHeight() + 1));
    buttonPanel.add(loadFileField);

    errorText = new JLabel();
    errorText.setForeground(Color.RED);
    errorText.setPreferredSize(new Dimension(300, (int) loadButton.getPreferredSize().getHeight() + 1));
    buttonPanel.add(errorText);

    contentPane.add(buttonPanel, BorderLayout.SOUTH);

    top.pack();
    top.setVisible(true);
  }

  private class GraphPanel extends Canvas {

    public GraphPanel() {
      setMinimumSize(new Dimension(800, 500));
      setPreferredSize(new Dimension(800, 500));
    }

    @Override
    public void paint(Graphics g) {
      g.setColor(BG_COLOR);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      g.setColor(Color.BLACK);
      g.fillOval(200, 200, 50, 50);
    }

  }

  private class DefaultMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent me) {
      Object source = me.getSource();

      try {
        if (source.equals(zoomInButton)) {
          zoomLevel++;
        }
        else if (source.equals(zoomOutButton)) {
          zoomLevel--;
          zoomLevel = zoomLevel < 0 ? 0 : zoomLevel;
        }
        else if (source.equals(saveButton)) {
          String loadFilename = loadFileField.getText();
          if (loadFilename == null || loadFilename.equals("")) {
            showError("No filename");
          }
          else {
            store.save(basis);
          }
        }
        else if (source.equals(loadButton)) {
          String loadFilename = loadFileField.getText();
          if (loadFilename == null || loadFilename.equals("")) {
            showError("No filename");
          }
          else {
            basis = store.load(loadFilename);
          }
        }
      }
      catch (PersistenceException e) {
        showError("Filesystem error");
      }
    }
    
  }

  /**
   * Sets the error message and then sets it back to "" again after a time
   * delay.
   */
  private void showError(String text) {
    errorText.setText(text);
    new Timer(3000, new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        errorText.setText("");
      }

    }).start();
  }

}
