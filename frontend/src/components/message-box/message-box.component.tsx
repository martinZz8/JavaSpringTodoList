import React, {ReactElement} from "react";

// styles
import styles from "./message-box.module.scss";

// interfaces
interface IMessageBox {
   message: string;
   link?: ReactElement;
   wide?: boolean;
   isError?: boolean;
   onCloseClick?: () => void;
}

const MessageBox: React.FC<IMessageBox> = ({
    message,
    link,
    wide,
    isError,
    onCloseClick
   }) => {

   return (
      <div className={`
         ${styles.messageBox}
         ${wide ? styles.wide : ""}
         ${isError ? styles.colorRed : styles.colorGreen}
      `}>
         <p>{message}{link ? link : ""}</p>
         {
            onCloseClick ?
               <div
                  className={styles.closeCross}
                  onClick={() => onCloseClick && onCloseClick()}
               >
                  <i className={"icon-cancel"}/>
               </div>
            :
               null
         }
      </div>
   );
};

export default MessageBox;