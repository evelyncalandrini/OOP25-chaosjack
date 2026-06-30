
package it.unibo.chaosjack.view.impl;

import it.unibo.chaosjack.model.api.Card;
import it.unibo.chaosjack.model.api.CardModifier;
import it.unibo.chaosjack.model.impl.Rank;
import it.unibo.chaosjack.view.api.CardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Rappresentazione grafica di una carta da gioco in JavaFX.
 * Implementa l'interfaccia CardView ed estende StackPane.
 */
public final class CardViewImpl extends StackPane implements CardView {

    private static final double WIDTH = 100;
    private static final double HEIGHT = 145;

    private static final int CORNER_RADIUS = 12;
    private static final double BORDER_STD = 1.2;
    private static final double BORDER_GHOST = 2.0;
    private static final double BORDER_REVERSE = 3.0;
    private static final double BORDER_BUST = 4.0;
    private static final double GHOST_GRAY = 0.96;
    private static final double GHOST_ALPHA = 0.45;
    private static final double SHADOW_ALPHA = 0.15;
    private static final int SHADOW_RADIUS = 6;
    private static final int SHADOW_OFFSET_X = 2;
    private static final int SHADOW_OFFSET_Y = 3;
    private static final int CONTENT_PADDING = 6;
    private static final int BADGE_W = 40;
    private static final int BADGE_H = 50;
    private static final double MAGNET_SCALE = 1.4;
    private static final double REVERSE_SCALE = 1.3;
    private static final int ROTATE_DEG = 180;
    private static final int HBOX_SPACING = 4;
    private static final int TOP_PADDING = 4;

    private static final String UNKNOWN_SYMBOL = "?";
    private static final String FX_TEXT_FILL = "-fx-text-fill: ";

    /**
     * Creates a new CardView for the specified Card.
     *
     * @param card the card to render
     */
    public CardViewImpl(final Card card) {
        // Proprietà dimensionali
        this.setPrefSize(WIDTH, HEIGHT);
        this.setMinSize(WIDTH, HEIGHT);
        this.setMaxSize(WIDTH, HEIGHT);

        // Estrazione dati
        final String cardName = card.getName();
        final CardModifier modifier = card.getModifier();

        String suitSymbol = UNKNOWN_SYMBOL;
        String suitColor = "black";
        boolean isRed = false;

        if (cardName.contains("HEARTS")) {
            suitSymbol = "♥";
            suitColor = "#d32f2f"; // Rosso classico
            isRed = true;
        } else if (cardName.contains("DIAMONDS")) {
            suitSymbol = "♦";
            suitColor = "#d32f2f";
            isRed = true;
        } else if (cardName.contains("CLUBS")) {
            suitSymbol = "♣";
            suitColor = "#212121";
        } else if (cardName.contains("SPADES")) {
            suitSymbol = "♠";
            suitColor = "#212121";
        }

        String rankSymbol = UNKNOWN_SYMBOL;
        for (final Rank r : Rank.values()) {
            if (cardName.contains(r.name())) {
                rankSymbol = getRankSymbol(r);
                break;
            }
        }

        // 1. Sfondo Solido o Semi-trasparente (Rectangle)
        final Rectangle background = new Rectangle(WIDTH, HEIGHT);
        background.setArcWidth(CORNER_RADIUS);
        background.setArcHeight(CORNER_RADIUS);

        javafx.scene.paint.Paint bgPaint = Color.WHITE;
        Color strokeColor = Color.web("#cccccc");
        double strokeWidth = BORDER_STD;
        String textFillColor = suitColor;

        switch (modifier) {
            case BUST_MAGNET:
                bgPaint = Color.WHITE; // Sfondo bianco uguale alle altre carte
                strokeColor = Color.web("#8e0000"); // Bordo rosso scuro
                strokeWidth = BORDER_BUST; // Bordo ancora più spesso rispetto alle altre (4.0 contro 2.0/1.2)
                textFillColor = suitColor; // Colore del seme (rosso o nero)
                break;
            case REVERSE:
                bgPaint = Color.WHITE; // Sfondo bianco uguale alle altre
                strokeColor = Color.web("#1565c0"); // Bordo blu scuro elegante
                strokeWidth = BORDER_REVERSE; // Bordo spesso (3.0 per REVERSE, 4.0 per BUST, 1.2 per standard)
                textFillColor = suitColor; // Colore normale del seme su sfondo chiaro
                break;
            case GHOST:
                // Effetto Fumo: Sfondo semi-trasparente bianco/grigio (45% opacità)
                bgPaint = Color.color(GHOST_GRAY, GHOST_GRAY, GHOST_GRAY, GHOST_ALPHA);
                strokeColor = Color.web("#9e9e9e"); // Bordo grigio
                strokeWidth = BORDER_GHOST;
                // Testo del colore del seme ma semitrasparente (più opaco dello sfondo: 75% opacità)
                textFillColor = isRed ? "rgba(211, 47, 47, 0.75)" : "rgba(33, 33, 33, 0.75)";
                break;
            case NONE:
                bgPaint = Color.WHITE;
                strokeColor = Color.web("#cccccc");
                strokeWidth = BORDER_STD;
                textFillColor = suitColor;
                break;
        }

        background.setFill(bgPaint);
        background.setStroke(strokeColor);
        background.setStrokeWidth(strokeWidth);
        this.getChildren().add(background);

        // Ombreggiatura (rimossa per la GHOST per dare un effetto spettrale più realistico, mantenuta per le altre)
        if (modifier != CardModifier.GHOST) {
            final DropShadow shadow = new DropShadow();
            shadow.setColor(Color.color(0, 0, 0, SHADOW_ALPHA));
            shadow.setRadius(SHADOW_RADIUS);
            shadow.setOffsetX(SHADOW_OFFSET_X);
            shadow.setOffsetY(SHADOW_OFFSET_Y);
            this.setEffect(shadow);
        }

        // 2. Costruzione del layout del contenuto
        final BorderPane cardContent = new BorderPane();
        cardContent.setPadding(new Insets(CONTENT_PADDING));

        // Angolo in alto a sinistra: Rango e Seme
        final Label topLeftLabel = new Label(rankSymbol + "\n" + suitSymbol);
        topLeftLabel.setStyle(FX_TEXT_FILL + textFillColor + "; -fx-font-size: 13px;"
            + " -fx-font-weight: bold; -fx-line-spacing: -2;");
        BorderPane.setAlignment(topLeftLabel, Pos.TOP_LEFT);
        cardContent.setTop(topLeftLabel);

        // Centro: Seme grande per le carte normali, calamita per Bust Magnet, o frecce per Reverse
        if (modifier == CardModifier.BUST_MAGNET) {
            final StackPane magnetPane = new StackPane();
            magnetPane.setMaxSize(BADGE_W, BADGE_H);

            final javafx.scene.shape.SVGPath leftHalf = new javafx.scene.shape.SVGPath();
            leftHalf.setContent("M 10 10 H 16 V 32 A 4 4 0 0 0 20 36 V 42 A 10 10 0 0 1 10 32 Z");
            leftHalf.setFill(Color.web("#8e0000")); // Polo Nord - Rosso scuro in linea con il bordo

            final javafx.scene.shape.SVGPath rightHalf = new javafx.scene.shape.SVGPath();
            rightHalf.setContent("M 30 10 H 24 V 32 A 4 4 0 0 1 20 36 V 42 A 10 10 0 0 0 30 32 Z");
            rightHalf.setFill(Color.web("#757575")); // Polo Sud - Grigio metallo

            final javafx.scene.Group magnetGroup = new javafx.scene.Group(leftHalf, rightHalf);
            magnetGroup.setScaleX(MAGNET_SCALE);
            magnetGroup.setScaleY(MAGNET_SCALE);

            magnetPane.getChildren().add(magnetGroup);
            cardContent.setCenter(magnetPane);
        } else if (modifier == CardModifier.REVERSE) {
            final StackPane reversePane = new StackPane();
            reversePane.setMaxSize(BADGE_W, BADGE_H);

            final javafx.scene.shape.SVGPath arrows = new javafx.scene.shape.SVGPath();
            arrows.setContent("M 20 10 C 25 10, 35 15, 35 25 H 39 L 34 32 L 29 25 H 33 "
                + "C 33 18, 25 14, 20 14 Z M 20 40 C 15 40, 5 35, 5 25 H 1 "
                + "L 6 18 L 11 25 H 7 C 7 32, 15 36, 20 36 Z");
            arrows.setFill(Color.web("#1565c0")); // Frecce blu in linea con il bordo della carta

            final javafx.scene.Group arrowsGroup = new javafx.scene.Group(arrows);
            arrowsGroup.setScaleX(REVERSE_SCALE);
            arrowsGroup.setScaleY(REVERSE_SCALE);

            reversePane.getChildren().add(arrowsGroup);
            cardContent.setCenter(reversePane);
        } else {
            final Label centerLabel = new Label(suitSymbol);
            centerLabel.setStyle(FX_TEXT_FILL + textFillColor + "; -fx-font-size: 44px;");
            cardContent.setCenter(centerLabel);
        }

        // Angolo in basso a destra (ruotato di 180 gradi)
        final Label bottomRightLabel = new Label(rankSymbol + "\n" + suitSymbol);
        bottomRightLabel.setStyle(FX_TEXT_FILL + textFillColor + "; -fx-font-size: 13px;"
            + " -fx-font-weight: bold; -fx-line-spacing: -2;");
        bottomRightLabel.setRotate(ROTATE_DEG);
        BorderPane.setAlignment(bottomRightLabel, Pos.BOTTOM_RIGHT);
        cardContent.setBottom(bottomRightLabel);

        this.getChildren().add(cardContent);

        // 3. Badge identificativo del modificatore in alto a destra
        if (modifier != CardModifier.NONE) {
            final HBox topBadges = new HBox(HBOX_SPACING);
            topBadges.setAlignment(Pos.TOP_RIGHT);
            topBadges.setPadding(new Insets(TOP_PADDING, TOP_PADDING, 0, 0));

            final Label modBadge = new Label();
            String modStyle = "";
            switch (modifier) {
                case BUST_MAGNET:
                    modBadge.setText("BUST");
                    modStyle = "-fx-background-color: #b71c1c; -fx-text-fill: white; -fx-font-size: 8px;"
                        + " -fx-font-weight: bold; -fx-padding: 1 4; -fx-background-radius: 3;";
                    break;
                case REVERSE:
                    modBadge.setText("REV");
                    modStyle = "-fx-background-color: #1565c0; -fx-text-fill: white; -fx-font-size: 8px;"
                        + " -fx-font-weight: bold; -fx-padding: 1 4; -fx-background-radius: 3;";
                    break;
                case GHOST:
                    modBadge.setText("GHOST");
                    // Badge per Ghost anch'esso leggermente semitrasparente in linea con l'effetto fumo
                    modStyle = "-fx-background-color: rgba(66, 66, 66, 0.75); -fx-text-fill: white;"
                        + " -fx-font-size: 8px; -fx-font-weight: bold; -fx-padding: 1 4; -fx-background-radius: 3;";
                    break;
                case NONE:
                    break;
            }
            modBadge.setStyle(modStyle);
            topBadges.getChildren().add(modBadge);

            setAlignment(topBadges, Pos.TOP_RIGHT);
            this.getChildren().add(topBadges);
        }
    }

    private String getRankSymbol(final Rank rank) {
        switch (rank) {
            case ACE: return "A";
            case TWO: return "2";
            case THREE: return "3";
            case FOUR: return "4";
            case FIVE: return "5";
            case SIX: return "6";
            case SEVEN: return "7";
            case EIGHT: return "8";
            case NINE: return "9";
            case TEN: return "10";
            case JACK: return "J";
            case QUEEN: return "Q";
            case KING: return "K";
        }
        return UNKNOWN_SYMBOL;
    }

    @Override
    public Parent getRootNode() {
        return this;
    }
}
