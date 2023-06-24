import React from "react";
import {animateScroll as scroll} from "react-scroll";

// styles
import styles from "./back-to-top-button.module.scss";

// hooks
import useBackToTopButton from "./back-to-top-button.hook";

const BackToTopButton: React.FC = () => {
   const {isShowed} = useBackToTopButton();

   return (
      <div
         className={`
            ${styles.buttonWrap}
            ${isShowed ? styles.isShowed : ""}
         `}
         onClick={() => scroll.scrollToTop({
            duration: 500
         })}
         title="Idź do góry"
      >
         <i className={"icon-down-open"}/>
      </div>
   );
};

export default BackToTopButton;