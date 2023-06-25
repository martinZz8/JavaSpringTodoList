import React from "react";

// styles
import styles from "./add-item-modal.module.scss";

// hooks
import useAddItemModal from "./add-item-modal.hook";

// components
import TemplateActionModal from "../../../../modals/action-modal/action-modal.template";
import MessageBox from "../../../../components/message-box/message-box.component";
import InputField from "../../../../components/ui/input-field/input-field.component";
import TextArea from "../../../../components/ui/text-area/text-area.component";
import Checkbox from "../../../../components/ui/checkbox/checkbox.component";

// interfaces
import {IItem} from "../main-page-content.types";

interface IAddItemModal {
  isOpened: boolean;
  locallyAddItem: (item: IItem) => void;
  closeModal: () => void;
}

const AddItemModal: React.FC<IAddItemModal> = ({
  isOpened,
  locallyAddItem,
  closeModal
}) => {
  const {
    addItemForm,
    onInputChange,
    addItemToDBAndLoc,
    isLoading,
    isDuplicatedNameError,
    setIsDuplicatedNameError
  } = useAddItemModal(isOpened, locallyAddItem, closeModal);

  return (
    <TemplateActionModal
      title="Dodawanie nowego zadania"
      isOpened={isOpened}
      isLoading={isLoading}
      onSubmitClick={addItemToDBAndLoc}
      onCancelClick={() => {
        closeModal();
        setIsDuplicatedNameError(false);
      }}
      submitButtonTitle="Dodaj"
      cancelButtonTitle="Wróć"
    >
      <div className={styles.wrapper}>
        <div className={styles.inputWrapper}>
          <InputField
            type="text"
            name="name"
            label="Nazwa"
            labelColor="black"
            value={addItemForm.name}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.inputWrapper}>
          <TextArea
            name="description"
            label="Opis"
            value={addItemForm.description}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.inputWrapper}>
          <Checkbox
            name="isDone"
            value="done"
            label="Zadanie wykonane"
            labelColor="black"
            handleChange={(name, value, checked) => onInputChange(name, checked)}
            checked={addItemForm.isDone}
          />
        </div>
        {
          isDuplicatedNameError ?
            <div className={styles.errorMessageWrap}>
              <MessageBox
                message="Zduplikowana nazwa - wybierz inną"
                onCloseClick={() => setIsDuplicatedNameError(false)}
                wide
                isError
              />
            </div>
          :
            null
        }
      </div>
    </TemplateActionModal>
  )
};

export default AddItemModal;