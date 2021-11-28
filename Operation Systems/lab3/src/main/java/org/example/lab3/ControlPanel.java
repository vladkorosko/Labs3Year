package org.example.lab3;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ControlPanel extends Frame
{
    Kernel kernel ;
    Button runButton = new Button("run");
    Button stepButton = new Button("step");
    Button resetButton = new Button("reset");
    Button exitButton = new Button("exit");

    Button b0 = new Button("page " + (0));
    Button b1 = new Button("page " + (1));
    Button b2 = new Button("page " + (2));
    Button b3 = new Button("page " + (3));
    Button b4 = new Button("page " + (4));
    Button b5 = new Button("page " + (5));
    Button b6 = new Button("page " + (6));
    Button b7 = new Button("page " + (7));
    Button b8 = new Button("page " + (8));
    Button b9 = new Button("page " + (9));
    Button b10 = new Button("page " + (10));
    Button b11 = new Button("page " + (11));
    Button b12 = new Button("page " + (12));
    Button b13 = new Button("page " + (13));
    Button b14 = new Button("page " + (14));
    Button b15 = new Button("page " + (15));
    Button b16 = new Button("page " + (16));
    Button b17 = new Button("page " + (17));
    Button b18 = new Button("page " + (18));
    Button b19 = new Button("page " + (19));
    Button b20 = new Button("page " + (20));
    Button b21 = new Button("page " + (21));
    Button b22 = new Button("page " + (22));
    Button b23 = new Button("page " + (23));
    Button b24 = new Button("page " + (24));
    Button b25 = new Button("page " + (25));
    Button b26 = new Button("page " + (26));
    Button b27 = new Button("page " + (27));
    Button b28 = new Button("page " + (28));
    Button b29 = new Button("page " + (29));
    Button b30 = new Button("page " + (30));
    Button b31 = new Button("page " + (31));
    Button b32 = new Button("page " + (32));
    Button b33 = new Button("page " + (33));
    Button b34 = new Button("page " + (34));
    Button b35 = new Button("page " + (35));
    Button b36 = new Button("page " + (36));
    Button b37 = new Button("page " + (37));
    Button b38 = new Button("page " + (38));
    Button b39 = new Button("page " + (39));
    Button b40 = new Button("page " + (40));
    Button b41 = new Button("page " + (41));
    Button b42 = new Button("page " + (42));
    Button b43 = new Button("page " + (43));
    Button b44 = new Button("page " + (44));
    Button b45 = new Button("page " + (45));
    Button b46 = new Button("page " + (46));
    Button b47 = new Button("page " + (47));
    Button b48 = new Button("page " + (48));
    Button b49 = new Button("page " + (49));
    Button b50 = new Button("page " + (50));
    Button b51 = new Button("page " + (51));
    Button b52 = new Button("page " + (52));
    Button b53 = new Button("page " + (53));
    Button b54 = new Button("page " + (54));
    Button b55 = new Button("page " + (55));
    Button b56 = new Button("page " + (56));
    Button b57 = new Button("page " + (57));
    Button b58 = new Button("page " + (58));
    Button b59 = new Button("page " + (59));
    Button b60 = new Button("page " + (60));
    Button b61 = new Button("page " + (61));
    Button b62 = new Button("page " + (62));
    Button b63 = new Button("page " + (63));
    Label statusValueLabel = new Label("STOP" , Label.LEFT) ;
    Label timeValueLabel = new Label("0" , Label.LEFT) ;
    Label instructionValueLabel = new Label("NONE" , Label.LEFT) ;
    Label addressValueLabel = new Label("NULL" , Label.LEFT) ;
    Label pageFaultValueLabel = new Label("NO" , Label.LEFT) ;
    Label virtualPageValueLabel = new Label("x" , Label.LEFT) ;
    Label physicalPageValueLabel = new Label("0" , Label.LEFT) ;
    Label RValueLabel = new Label("0" , Label.LEFT) ;
    Label MValueLabel = new Label("0" , Label.LEFT) ;
    Label inMemTimeValueLabel = new Label("0" , Label.LEFT) ;
    Label lastTouchTimeValueLabel = new Label("0" , Label.LEFT) ;
    Label lowValueLabel = new Label("0" , Label.LEFT) ;
    Label highValueLabel = new Label("0" , Label.LEFT) ;

    List<Label> labels;

    public ControlPanel()
    {
        super();
    }

    public ControlPanel( String title )
    {
        super(title);
    }

    public void init( Kernel useKernel , String commands , String config )
    {
        kernel = useKernel ;
        kernel.setControlPanel( this );
        setLayout( null );
        setBackground( Color.white );
        setForeground( Color.black );
        setSize( 635 , 545 );
        setFont( new Font( "Courier", Font.PLAIN, 12 ) );

        {
            runButton.setForeground(Color.blue);
            runButton.setBackground(Color.lightGray);
            runButton.setBounds(0, 25, 70, 15);
            add(runButton);

            stepButton.setForeground(Color.blue);
            stepButton.setBackground(Color.lightGray);
            stepButton.setBounds(70, 25, 70, 15);
            add(stepButton);

            resetButton.setForeground(Color.blue);
            resetButton.setBackground(Color.lightGray);
            resetButton.setBounds(140, 25, 70, 15);
            add(resetButton);

            exitButton.setForeground(Color.blue);
            exitButton.setBackground(Color.lightGray);
            exitButton.setBounds(210, 25, 70, 15);
            add(exitButton);

            b0.setBounds(0, (2) * 15 + 25, 70, 15);
            b0.setForeground(Color.magenta);
            b0.setBackground(Color.lightGray);
            add(b0);

            b1.setBounds(0, (1 + 2) * 15 + 25, 70, 15);
            b1.setForeground(Color.magenta);
            b1.setBackground(Color.lightGray);
            add(b1);

            b2.setBounds(0, (2 + 2) * 15 + 25, 70, 15);
            b2.setForeground(Color.magenta);
            b2.setBackground(Color.lightGray);
            add(b2);

            b3.setBounds(0, (3 + 2) * 15 + 25, 70, 15);
            b3.setForeground(Color.magenta);
            b3.setBackground(Color.lightGray);
            add(b3);

            b4.setBounds(0, (4 + 2) * 15 + 25, 70, 15);
            b4.setForeground(Color.magenta);
            b4.setBackground(Color.lightGray);
            add(b4);

            b5.setBounds(0, (5 + 2) * 15 + 25, 70, 15);
            b5.setForeground(Color.magenta);
            b5.setBackground(Color.lightGray);
            add(b5);

            b6.setBounds(0, (6 + 2) * 15 + 25, 70, 15);
            b6.setForeground(Color.magenta);
            b6.setBackground(Color.lightGray);
            add(b6);

            b7.setBounds(0, (7 + 2) * 15 + 25, 70, 15);
            b7.setForeground(Color.magenta);
            b7.setBackground(Color.lightGray);
            add(b7);

            b8.setBounds(0, (8 + 2) * 15 + 25, 70, 15);
            b8.setForeground(Color.magenta);
            b8.setBackground(Color.lightGray);
            add(b8);

            b9.setBounds(0, (9 + 2) * 15 + 25, 70, 15);
            b9.setForeground(Color.magenta);
            b9.setBackground(Color.lightGray);
            add(b9);

            b10.setBounds(0, (10 + 2) * 15 + 25, 70, 15);
            b10.setForeground(Color.magenta);
            b10.setBackground(Color.lightGray);
            add(b10);

            b11.setBounds(0, (11 + 2) * 15 + 25, 70, 15);
            b11.setForeground(Color.magenta);
            b11.setBackground(Color.lightGray);
            add(b11);

            b12.setBounds(0, (12 + 2) * 15 + 25, 70, 15);
            b12.setForeground(Color.magenta);
            b12.setBackground(Color.lightGray);
            add(b12);

            b13.setBounds(0, (13 + 2) * 15 + 25, 70, 15);
            b13.setForeground(Color.magenta);
            b13.setBackground(Color.lightGray);
            add(b13);

            b14.setBounds(0, (14 + 2) * 15 + 25, 70, 15);
            b14.setForeground(Color.magenta);
            b14.setBackground(Color.lightGray);
            add(b14);

            b15.setBounds(0, (15 + 2) * 15 + 25, 70, 15);
            b15.setForeground(Color.magenta);
            b15.setBackground(Color.lightGray);
            add(b15);

            b16.setBounds(0, (16 + 2) * 15 + 25, 70, 15);
            b16.setForeground(Color.magenta);
            b16.setBackground(Color.lightGray);
            add(b16);

            b17.setBounds(0, (17 + 2) * 15 + 25, 70, 15);
            b17.setForeground(Color.magenta);
            b17.setBackground(Color.lightGray);
            add(b17);

            b18.setBounds(0, (18 + 2) * 15 + 25, 70, 15);
            b18.setForeground(Color.magenta);
            b18.setBackground(Color.lightGray);
            add(b18);

            b19.setBounds(0, (19 + 2) * 15 + 25, 70, 15);
            b19.setForeground(Color.magenta);
            b19.setBackground(Color.lightGray);
            add(b19);

            b20.setBounds(0, (20 + 2) * 15 + 25, 70, 15);
            b20.setForeground(Color.magenta);
            b20.setBackground(Color.lightGray);
            add(b20);

            b21.setBounds(0, (21 + 2) * 15 + 25, 70, 15);
            b21.setForeground(Color.magenta);
            b21.setBackground(Color.lightGray);
            add(b21);

            b22.setBounds(0, (22 + 2) * 15 + 25, 70, 15);
            b22.setForeground(Color.magenta);
            b22.setBackground(Color.lightGray);
            add(b22);

            b23.setBounds(0, (23 + 2) * 15 + 25, 70, 15);
            b23.setForeground(Color.magenta);
            b23.setBackground(Color.lightGray);
            add(b23);

            b24.setBounds(0, (24 + 2) * 15 + 25, 70, 15);
            b24.setForeground(Color.magenta);
            b24.setBackground(Color.lightGray);
            add(b24);

            b25.setBounds(0, (25 + 2) * 15 + 25, 70, 15);
            b25.setForeground(Color.magenta);
            b25.setBackground(Color.lightGray);
            add(b25);

            b26.setBounds(0, (26 + 2) * 15 + 25, 70, 15);
            b26.setForeground(Color.magenta);
            b26.setBackground(Color.lightGray);
            add(b26);

            b27.setBounds(0, (27 + 2) * 15 + 25, 70, 15);
            b27.setForeground(Color.magenta);
            b27.setBackground(Color.lightGray);
            add(b27);

            b28.setBounds(0, (28 + 2) * 15 + 25, 70, 15);
            b28.setForeground(Color.magenta);
            b28.setBackground(Color.lightGray);
            add(b28);

            b29.setBounds(0, (29 + 2) * 15 + 25, 70, 15);
            b29.setForeground(Color.magenta);
            b29.setBackground(Color.lightGray);
            add(b29);

            b30.setBounds(0, (30 + 2) * 15 + 25, 70, 15);
            b30.setForeground(Color.magenta);
            b30.setBackground(Color.lightGray);
            add(b30);

            b31.setBounds(0, (31 + 2) * 15 + 25, 70, 15);
            b31.setForeground(Color.magenta);
            b31.setBackground(Color.lightGray);
            add(b31);

            b32.setBounds(140, (2) * 15 + 25, 70, 15);
            b32.setForeground(Color.magenta);
            b32.setBackground(Color.lightGray);
            add(b32);

            b33.setBounds(140, (1 + 2) * 15 + 25, 70, 15);
            b33.setForeground(Color.magenta);
            b33.setBackground(Color.lightGray);
            add(b33);

            b34.setBounds(140, (2 + 2) * 15 + 25, 70, 15);
            b34.setForeground(Color.magenta);
            b34.setBackground(Color.lightGray);
            add(b34);

            b35.setBounds(140, (3 + 2) * 15 + 25, 70, 15);
            b35.setForeground(Color.magenta);
            b35.setBackground(Color.lightGray);
            add(b35);

            b36.setBounds(140, (4 + 2) * 15 + 25, 70, 15);
            b36.setForeground(Color.magenta);
            b36.setBackground(Color.lightGray);
            add(b36);

            b37.setBounds(140, (5 + 2) * 15 + 25, 70, 15);
            b37.setForeground(Color.magenta);
            b37.setBackground(Color.lightGray);
            add(b37);

            b38.setBounds(140, (6 + 2) * 15 + 25, 70, 15);
            b38.setForeground(Color.magenta);
            b38.setBackground(Color.lightGray);
            add(b38);

            b39.setBounds(140, (7 + 2) * 15 + 25, 70, 15);
            b39.setForeground(Color.magenta);
            b39.setBackground(Color.lightGray);
            add(b39);

            b40.setBounds(140, (8 + 2) * 15 + 25, 70, 15);
            b40.setForeground(Color.magenta);
            b40.setBackground(Color.lightGray);
            add(b40);

            b41.setBounds(140, (9 + 2) * 15 + 25, 70, 15);
            b41.setForeground(Color.magenta);
            b41.setBackground(Color.lightGray);
            add(b41);

            b42.setBounds(140, (10 + 2) * 15 + 25, 70, 15);
            b42.setForeground(Color.magenta);
            b42.setBackground(Color.lightGray);
            add(b42);

            b43.setBounds(140, (11 + 2) * 15 + 25, 70, 15);
            b43.setForeground(Color.magenta);
            b43.setBackground(Color.lightGray);
            add(b43);

            b44.setBounds(140, (12 + 2) * 15 + 25, 70, 15);
            b44.setForeground(Color.magenta);
            b44.setBackground(Color.lightGray);
            add(b44);

            b45.setBounds(140, (13 + 2) * 15 + 25, 70, 15);
            b45.setForeground(Color.magenta);
            b45.setBackground(Color.lightGray);
            add(b45);

            b46.setBounds(140, (14 + 2) * 15 + 25, 70, 15);
            b46.setForeground(Color.magenta);
            b46.setBackground(Color.lightGray);
            add(b46);

            b47.setBounds(140, (15 + 2) * 15 + 25, 70, 15);
            b47.setForeground(Color.magenta);
            b47.setBackground(Color.lightGray);
            add(b47);

            b48.setBounds(140, (16 + 2) * 15 + 25, 70, 15);
            b48.setForeground(Color.magenta);
            b48.setBackground(Color.lightGray);
            add(b48);

            b49.setBounds(140, (17 + 2) * 15 + 25, 70, 15);
            b49.setForeground(Color.magenta);
            b49.setBackground(Color.lightGray);
            add(b49);

            b50.setBounds(140, (18 + 2) * 15 + 25, 70, 15);
            b50.setForeground(Color.magenta);
            b50.setBackground(Color.lightGray);
            add(b50);

            b51.setBounds(140, (19 + 2) * 15 + 25, 70, 15);
            b51.setForeground(Color.magenta);
            b51.setBackground(Color.lightGray);
            add(b51);

            b52.setBounds(140, (20 + 2) * 15 + 25, 70, 15);
            b52.setForeground(Color.magenta);
            b52.setBackground(Color.lightGray);
            add(b52);

            b53.setBounds(140, (21 + 2) * 15 + 25, 70, 15);
            b53.setForeground(Color.magenta);
            b53.setBackground(Color.lightGray);
            add(b53);

            b54.setBounds(140, (22 + 2) * 15 + 25, 70, 15);
            b54.setForeground(Color.magenta);
            b54.setBackground(Color.lightGray);
            add(b54);

            b55.setBounds(140, (23 + 2) * 15 + 25, 70, 15);
            b55.setForeground(Color.magenta);
            b55.setBackground(Color.lightGray);
            add(b55);

            b56.setBounds(140, (24 + 2) * 15 + 25, 70, 15);
            b56.setForeground(Color.magenta);
            b56.setBackground(Color.lightGray);
            add(b56);

            b57.setBounds(140, (25 + 2) * 15 + 25, 70, 15);
            b57.setForeground(Color.magenta);
            b57.setBackground(Color.lightGray);
            add(b57);

            b58.setBounds(140, (26 + 2) * 15 + 25, 70, 15);
            b58.setForeground(Color.magenta);
            b58.setBackground(Color.lightGray);
            add(b58);

            b59.setBounds(140, (27 + 2) * 15 + 25, 70, 15);
            b59.setForeground(Color.magenta);
            b59.setBackground(Color.lightGray);
            add(b59);

            b60.setBounds(140, (28 + 2) * 15 + 25, 70, 15);
            b60.setForeground(Color.magenta);
            b60.setBackground(Color.lightGray);
            add(b60);

            b61.setBounds(140, (29 + 2) * 15 + 25, 70, 15);
            b61.setForeground(Color.magenta);
            b61.setBackground(Color.lightGray);
            add(b61);

            b62.setBounds(140, (30 + 2) * 15 + 25, 70, 15);
            b62.setForeground(Color.magenta);
            b62.setBackground(Color.lightGray);
            add(b62);

            b63.setBounds(140, (31 + 2) * 15 + 25, 70, 15);
            b63.setForeground(Color.magenta);
            b63.setBackground(Color.lightGray);
            add(b63);

            statusValueLabel.setBounds(345, 25, 100, 15);
            add(statusValueLabel);

            timeValueLabel.setBounds(345, 15 + 25, 100, 15);
            add(timeValueLabel);

            instructionValueLabel.setBounds(385, 45 + 25, 100, 15);
            add(instructionValueLabel);

            addressValueLabel.setBounds(385, 60 + 25, 230, 15);
            add(addressValueLabel);

            pageFaultValueLabel.setBounds(385, 90 + 25, 100, 15);
            add(pageFaultValueLabel);

            virtualPageValueLabel.setBounds(395, 120 + 25, 200, 15);
            add(virtualPageValueLabel);

            physicalPageValueLabel.setBounds(395, 135 + 25, 200, 15);
            add(physicalPageValueLabel);

            RValueLabel.setBounds(395, 150 + 25, 200, 15);
            add(RValueLabel);

            MValueLabel.setBounds(395, 165 + 25, 200, 15);
            add(MValueLabel);

            inMemTimeValueLabel.setBounds(395, 180 + 25, 200, 15);
            add(inMemTimeValueLabel);

            lastTouchTimeValueLabel.setBounds(395, 195 + 25, 200, 15);
            add(lastTouchTimeValueLabel);

            lowValueLabel.setBounds(395, 210 + 25, 230, 15);
            add(lowValueLabel);

            highValueLabel.setBounds(395, 225 + 25, 230, 15);
            add(highValueLabel);

            Label virtualOneLabel = new Label("virtual", Label.CENTER);
            virtualOneLabel.setBounds(0, 15 + 25, 70, 15);
            add(virtualOneLabel);

            Label virtualTwoLabel = new Label("virtual", Label.CENTER);
            virtualTwoLabel.setBounds(140, 15 + 25, 70, 15);
            add(virtualTwoLabel);

            Label physicalOneLabel = new Label("physical", Label.CENTER);
            physicalOneLabel.setBounds(70, 15 + 25, 70, 15);
            add(physicalOneLabel);

            Label physicalTwoLabel = new Label("physical", Label.CENTER);
            physicalTwoLabel.setBounds(210, 15 + 25, 70, 15);
            add(physicalTwoLabel);

            Label statusLabel = new Label("status: ", Label.LEFT);
            statusLabel.setBounds(285, 25, 65, 15);
            add(statusLabel);

            Label timeLabel = new Label("time: ", Label.LEFT);
            timeLabel.setBounds(285, 15 + 25, 50, 15);
            add(timeLabel);

            Label instructionLabel = new Label("instruction: ", Label.LEFT);
            instructionLabel.setBounds(285, 45 + 25, 100, 15);
            add(instructionLabel);

            Label addressLabel = new Label("address: ", Label.LEFT);
            addressLabel.setBounds(285, 60 + 25, 85, 15);
            add(addressLabel);

            Label pageFaultLabel = new Label("page fault: ", Label.LEFT);
            pageFaultLabel.setBounds(285, 90 + 25, 100, 15);
            add(pageFaultLabel);

            Label virtualPageLabel = new Label("virtual page: ", Label.LEFT);
            virtualPageLabel.setBounds(285, 120 + 25, 110, 15);
            add(virtualPageLabel);

            Label physicalPageLabel = new Label("physical page: ", Label.LEFT);
            physicalPageLabel.setBounds(285, 135 + 25, 110, 15);
            add(physicalPageLabel);

            Label RLabel = new Label("R: ", Label.LEFT);
            RLabel.setBounds(285, 150 + 25, 110, 15);
            add(RLabel);

            Label MLabel = new Label("M: ", Label.LEFT);
            MLabel.setBounds(285, 165 + 25, 110, 15);
            add(MLabel);

            Label inMemTimeLabel = new Label("inMemTime: ", Label.LEFT);
            inMemTimeLabel.setBounds(285, 180 + 25, 110, 15);
            add(inMemTimeLabel);

            Label lastTouchTimeLabel = new Label("lastTouchTime: ", Label.LEFT);
            lastTouchTimeLabel.setBounds(285, 195 + 25, 110, 15);
            add(lastTouchTimeLabel);

            Label lowLabel = new Label("low: ", Label.LEFT);
            lowLabel.setBounds(285, 210 + 25, 110, 15);
            add(lowLabel);

            Label highLabel = new Label("high: ", Label.LEFT);
            highLabel.setBounds(285, 225 + 25, 110, 15);
            add(highLabel);

            labels = IntStream.range(0, 64)
                    .boxed()
                    .map(i -> {
                        Label label = new Label(null, Label.CENTER);
                        label.setBounds(i / 32 == 0 ? 70 : 210, (i % 32 + 2) * 15 + 25, 60, 15);
                        label.setForeground(Color.red);
                        label.setFont(new Font("Courier", Font.PLAIN, 10));
                        add(label);
                        return label;
                    })
                    .collect(Collectors.toList());
        }

        kernel.init( commands , config );

        setVisible(true);
    }

    public void paintPage( Page page )
    {
        virtualPageValueLabel.setText( Integer.toString( page.id ) );
        physicalPageValueLabel.setText( Integer.toString( page.physical ) );
        RValueLabel.setText( Integer.toString( page.R ) );
        MValueLabel.setText( Integer.toString( page.M ) );
        inMemTimeValueLabel.setText( Integer.toString( page.inMemTime ) );
        lastTouchTimeValueLabel.setText( Integer.toString( page.lastTouchTime ) );
        lowValueLabel.setText(Long.toString( page.low , Kernel.addressRadix) );
        highValueLabel.setText(Long.toString( page.high , Kernel.addressRadix) );
    }

    public void setStatus(String status) {
        statusValueLabel.setText(status);
    }

    public void addPhysicalPage( int pageNum , int physicalPage )
    {
        if (physicalPage >= 0 && physicalPage < 64){
            labels.get(physicalPage).setText("page " + pageNum);
        } else {
            return;
        }
    }

    public void removePhysicalPage( int physicalPage )
    {
        if (physicalPage >= 0 && physicalPage < 64){
            labels.get(physicalPage).setText(null);
        } else {
            return;
        }
    }


    public boolean action( Event e, Object arg )
    {
        if ( e.target == runButton )
        {
            setStatus( "RUN" );
            runButton.setEnabled(false);
            stepButton.setEnabled(false);
            resetButton.setEnabled(false);
            kernel.start();
            setStatus( "STOP" );
            resetButton.setEnabled(true);
            return true;
        }
        else if ( e.target == stepButton )
        {
            setStatus( "STEP" );
            kernel.step();
            if (kernel.runCycles == kernel.runs) {
                stepButton.setEnabled(false);
                runButton.setEnabled(false);
            }
            setStatus("STOP");
            return true;
        }
        else if ( e.target == resetButton )
        {
            kernel.reset();
            runButton.setEnabled(true);
            stepButton.setEnabled(true);
            return true;
        }
        else if ( e.target == exitButton )
        {
            System.exit(0);
            return true;
        }
        else if ( e.target == b0 )
        {
            kernel.getPage(0);
            return true;
        }
        else if ( e.target == b1 )
        {
            kernel.getPage(1);
            return true;
        }
        else if ( e.target == b2 )
        {
            kernel.getPage(2);
            return true;
        }
        else if ( e.target == b3 )
        {
            kernel.getPage(3);
            return true;
        }
        else if ( e.target == b4 )
        {
            kernel.getPage(4);
            return true;
        }
        else if ( e.target == b5 )
        {
            kernel.getPage(5);
            return true;
        }
        else if ( e.target == b6 )
        {
            kernel.getPage(6);
            return true;
        }
        else if ( e.target == b7 )
        {
            kernel.getPage(7);
            return true;
        }
        else if ( e.target == b8 )
        {
            kernel.getPage(8);
            return true;
        }
        else if ( e.target == b9 )
        {
            kernel.getPage(9);
            return true;
        }
        else if ( e.target == b10 )
        {
            kernel.getPage(10);
            return true;
        }
        else if ( e.target == b11 )
        {
            kernel.getPage(11);
            return true;
        }
        else if ( e.target == b12 )
        {
            kernel.getPage(12);
            return true;
        }
        else if ( e.target == b13 )
        {
            kernel.getPage(13);
            return true;
        }
        else if ( e.target == b14 )
        {
            kernel.getPage(14);
            return true;
        }
        else if ( e.target == b15 )
        {
            kernel.getPage(15);
            return true;
        }
        else if ( e.target == b16 )
        {
            kernel.getPage(16);
            return true;
        }
        else if ( e.target == b17 )
        {
            kernel.getPage(17);
            return true;
        }
        else if ( e.target == b18 )
        {
            kernel.getPage(18);
            return true;
        }
        else if ( e.target == b19 )
        {
            kernel.getPage(19);
            return true;
        }
        else if ( e.target == b20 )
        {
            kernel.getPage(20);
            return true;
        }
        else if ( e.target == b21 )
        {
            kernel.getPage(21);
            return true;
        }
        else if ( e.target == b22 )
        {
            kernel.getPage(22);
            return true;
        }
        else if ( e.target == b23 )
        {
            kernel.getPage(23);
            return true;
        }
        else if ( e.target == b24 )
        {
            kernel.getPage(24);
            return true;
        }
        else if ( e.target == b25 )
        {
            kernel.getPage(25);
            return true;
        }
        else if ( e.target == b26 )
        {
            kernel.getPage(26);
            return true;
        }
        else if ( e.target == b27 )
        {
            kernel.getPage(27);
            return true;
        }
        else if ( e.target == b28 )
        {
            kernel.getPage(28);
            return true;
        }
        else if ( e.target == b29 )
        {
            kernel.getPage(29);
            return true;
        }
        else if ( e.target == b30 )
        {
            kernel.getPage(30);
            return true;
        }
        else if ( e.target == b31 )
        {
            kernel.getPage(31);
            return true;
        }
        else if ( e.target == b32 )
        {
            kernel.getPage(32);
            return true;
        }
        else if ( e.target == b33 )
        {
            kernel.getPage(33);
            return true;
        }
        else if ( e.target == b34 )
        {
            kernel.getPage(34);
            return true;
        }
        else if ( e.target == b35 )
        {
            kernel.getPage(35);
            return true;
        }
        else if ( e.target == b36 )
        {
            kernel.getPage(36);
            return true;
        }
        else if ( e.target == b37 )
        {
            kernel.getPage(37);
            return true;
        }
        else if ( e.target == b38 )
        {
            kernel.getPage(38);
            return true;
        }
        else if ( e.target == b39 )
        {
            kernel.getPage(39);
            return true;
        }
        else if ( e.target == b40 )
        {
            kernel.getPage(40);
            return true;
        }
        else if ( e.target == b41 )
        {
            kernel.getPage(41);
            return true;
        }
        else if ( e.target == b42 )
        {
            kernel.getPage(42);
            return true;
        }
        else if ( e.target == b43 )
        {
            kernel.getPage(43);
            return true;
        }
        else if ( e.target == b44 )
        {
            kernel.getPage(44);
            return true;
        }
        else if ( e.target == b45 )
        {
            kernel.getPage(45);
            return true;
        }
        else if ( e.target == b46 )
        {
            kernel.getPage(46);
            return true;
        }
        else if ( e.target == b47 )
        {
            kernel.getPage(47);
            return true;
        }
        else if ( e.target == b48 )
        {
            kernel.getPage(48);
            return true;
        }
        else if ( e.target == b49 )
        {
            kernel.getPage(49);
            return true;
        }
        else if ( e.target == b50 )
        {
            kernel.getPage(50);
            return true;
        }
        else if ( e.target == b51 )
        {
            kernel.getPage(51);
            return true;
        }
        else if ( e.target == b52 )
        {
            kernel.getPage(52);
            return true;
        }
        else if ( e.target == b53 )
        {
            kernel.getPage(53);
            return true;
        }
        else if ( e.target == b54 )
        {
            kernel.getPage(54);
            return true;
        }
        else if ( e.target == b55 )
        {
            kernel.getPage(55);
            return true;
        }
        else if ( e.target == b56 )
        {
            kernel.getPage(56);
            return true;
        }
        else if ( e.target == b57 )
        {
            kernel.getPage(57);
            return true;
        }
        else if ( e.target == b58 )
        {
            kernel.getPage(58);
            return true;
        }
        else if ( e.target == b59 )
        {
            kernel.getPage(59);
            return true;
        }
        else if ( e.target == b60 )
        {
            kernel.getPage(60);
            return true;
        }
        else if ( e.target == b61 )
        {
            kernel.getPage(61);
            return true;
        }
        else if ( e.target == b62 )
        {
            kernel.getPage(62);
            return true;
        }
        else if ( e.target == b63 )
        {
            kernel.getPage(63);
            return true;
        }
        else
        {
            return false;
        }
    }
}

