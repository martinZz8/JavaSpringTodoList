import React from "react";

// styles
import styles from "./button.module.scss";

// interfaces
interface IButton {
  type: "submit" | "button";
  title: string;
  backgroundColor: "purple" | "lightPurple" | "black" | "red" | "darkerGray" | "lightGray";
  fontColor: "white" | "black";
  handleClick?: () => void;
  disabled?: boolean;
  noBorderRadius?: boolean;
  defaultCursor?: boolean;
  bigFont?: boolean;
  bigHeight?: boolean;
  isBoxShadow?: boolean;
}

const Button: React.FC<IButton> = ({
    type,
    title,
    backgroundColor,
    fontColor,
    handleClick,
    disabled,
    noBorderRadius,
    defaultCursor,
    bigFont,
    bigHeight,
    isBoxShadow
  }) => {
  const stylesBackgroundColor = backgroundColor === "lightPurple" ? styles.backgroundColorLightPurple : backgroundColor === "purple" ? styles.backgroundColorPurple : backgroundColor === "red" ? styles.backgroundColorRed : backgroundColor === "darkerGray" ? styles.backgroundColorDarkerGray : backgroundColor === "lightGray" ? styles.backgroundColorLightGray : styles.backgroundColorBlack;
  const stylesFontColor = fontColor === "white" ? styles.fontColorWhite : fontColor === "black" ? styles.fontColorBlack : styles.fontColorWhite;
  const hoverColors =
    fontColor === "white" ?
      backgroundColor === "lightPurple" ?
        styles.hoverColorsLightPurple
      : backgroundColor === "purple" ?
          styles.hoverColorsPurple
      : backgroundColor === "red" ?
        styles.hoverColorsRed
      : backgroundColor === "darkerGray" ?
        styles.hoverColorsDarkerGray
      :
        styles.hoverColorsBlack
    : //black font
      backgroundColor === "lightGray" ?
        styles.hoverColorsLightGray
      :
        styles.hoverColorsLightPurple;


  return (
    <div className={styles.buttonWrap}>
      <button
        className={`
          ${styles.button}
          ${!disabled ? stylesBackgroundColor : ""}
          ${!disabled ? stylesFontColor : ""}
          ${!disabled ? hoverColors : ""}
          ${disabled ? styles.disabledColors : ""}
          ${noBorderRadius ? styles.noBorderRadius : ""}
          ${defaultCursor ? styles.defaultCursor : ""}
          ${bigFont ? styles.bigFont : ""}
          ${bigHeight ? styles.bigHeight : ""}
          ${isBoxShadow ? styles.boxShadow : ""}
        `}
        type={type}
        onClick={() => handleClick && handleClick()}
        disabled={disabled}
      >
        {title}
      </button>
    </div>
  );
};

export default Button;