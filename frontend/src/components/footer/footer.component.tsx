import React from "react";

// styles
import styles from "./footer.module.scss";

const Footer: React.FC = () => {

   return (
      <div className={styles.footerWrap}>
        <div className={styles.footerInfo}>
           <div className={styles.item}>
              <p>
                "Wszelkie prawa zastrzeżone"
                 <span className={styles.copyright}> &copy;</span> {new Date().getFullYear()}
              </p>
           </div>
           <div className={styles.item}>
              <p>
                "Wykonane przez: "
                 <br/>
                 <a href="mailto: martinzz.info@gmail.com">Maciej Harbuz</a>
              </p>
           </div>
        </div>
      </div>
   );
};

export default Footer;