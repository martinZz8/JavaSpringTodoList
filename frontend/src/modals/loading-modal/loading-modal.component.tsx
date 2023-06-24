import React from "react";

// styles
import styles from "./loading-modal.module.scss";

// interfaces
interface ILoadingModal {
  message?: string;
  small?: boolean;
  bgTransparent?: boolean;
  bgSemiTransparent?: boolean;
}

const LoadingModal: React.FC<ILoadingModal> = ({
    message,
    small,
    bgTransparent,
    bgSemiTransparent
  }) => {

  return (
    <div className={`
      ${styles.loadingModal}
      ${bgTransparent ? styles.bgTransparent : ""}
      ${bgSemiTransparent ? styles.bgSemiTransparent : ""}
    `}>
      <div className={`
        loader
        ${small ? "small" : ""}
      `}/>
      {
        message ?
          <div className={styles.message}>
            <p>{message}</p>
          </div>
        :
          null
      }
    </div>
  );
};

export default LoadingModal;