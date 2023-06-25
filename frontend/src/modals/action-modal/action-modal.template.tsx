import React from "react";
import type { ReactNode } from "react";

// styles
import styles from "./action-modal.module.scss";

// templates
import TemplateBasicModal from "../basic-modal/basic-modal.template";

// components
import Button from "../../components/ui/button/button.component";
import LoadingModal from "../loading-modal/loading-modal.component";

// interfaces
interface IActionModal {
  title: string;
  submitButtonTitle: string;
  cancelButtonTitle: string;
  onSubmitClick: (e: React.FormEvent) => void;
  onCancelClick: () => void;
  isOpened: boolean;
  isLoading?: boolean;
  children: ReactNode;
  isSubmitButtonDisabled?: boolean;
}

const TemplateActionModal: React.FC<IActionModal> = ({
    title,
    submitButtonTitle,
    cancelButtonTitle,
    onSubmitClick,
    onCancelClick,
    isOpened,
    isLoading,
    children,
    isSubmitButtonDisabled
 }) => {
  
  return (
    <TemplateBasicModal
      isOpened={isOpened}
      onOutClick={
        !isLoading ?
          onCancelClick
        :
          undefined
      }
    >
      <form
        className={styles.modalContainer}
        onSubmit={!isSubmitButtonDisabled ? onSubmitClick : undefined}
        noValidate
      >
        <div className={styles.titleContainer}>
          {title}
        </div>
        <div className={styles.contentContainer}>
          {children}
        </div>
        <div className={styles.buttonsContainer}>
          <div className={styles.buttonWrapper}>
            <Button
              type="submit"
              title={submitButtonTitle}
              fontColor="black"
              backgroundColor="lightPurple"
              bigFont
              disabled={isSubmitButtonDisabled}
            />
          </div>
          <div className={styles.buttonWrapper}>
            <Button
              type="button"
              title={cancelButtonTitle}
              fontColor="black"
              backgroundColor="red"
              handleClick={onCancelClick}
              bigFont
            />
          </div>
          {
            isLoading ?
              <LoadingModal
                message="Wysyłanie..."
                bgSemiTransparent
              />
            :
              null
          }
        </div>
      </form>
    </TemplateBasicModal>
  );
};

export default TemplateActionModal;