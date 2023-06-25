import React from "react";
import {Link} from "react-router-dom";

// styles
import styles from "./todo-item.module.scss";

// hooks
import useTodoItem from "./todo-item.hook";

// components
import Checkbox from "../../../../components/ui/checkbox/checkbox.component";
import InputField from "../../../../components/ui/input-field/input-field.component";
import Button from "../../../../components/ui/button/button.component";

// interfaces
import {IItem} from "../main-page-content.types";
import TextArea from "../../../../components/ui/text-area/text-area.component";
import MessageBox from "../../../../components/message-box/message-box.component";
import DeleteItemModal from "../delete-item-modal/delete-item-modal.component";

interface ITodoItemComponent {
  item: IItem;
  locallyChangeItem: (value: IItem) => void,
  locallyDeleteItem: () => void,
}

const TodoItem: React.FC<ITodoItemComponent> = ({
    item,
    locallyChangeItem,
    locallyDeleteItem
  }) => {
  const {
    editValues,
    isEditOpened,
    onEditValueChange,
    toggleIsEditOpened,
    changeStatusOfItemInDBAndLoc,
    changeOtherDataOfItemInDBAndLoc,
    isErrorDuplicatedName,
    setIsErrorDuplicatedName,
    isDeleteItemModalOpened,
    setIsDeleteItemModalOpened
  } = useTodoItem(item, locallyChangeItem);

  return (
    <div className={styles.wrapper}>
      <div className={styles.main}>
        <Checkbox
          name="done"
          value="done"
          handleChange={(name, value, checked) => changeStatusOfItemInDBAndLoc(checked)}
          checked={item.done}
        />
        <div className={styles.spacedItems}>
          <div className={styles.dataWrapper}>
            <div className={styles.infoWrapper}>
              <p className={styles.title}>
                Nazwa:
              </p>
              <p>
                {item.name}
              </p>
            </div>
            <div className={styles.infoWrapper}>
              <p className={styles.title}>
                Opis:
              </p>
              <p>
                {item.description}
              </p>
            </div>
            <div className={styles.infoWrapper}>
              <p className={styles.title}>
                Ukończone:
              </p>
              <p>
                {item.done ? "tak" : "nie"}
              </p>
            </div>
          </div>
          <div className={styles.toolbar}>
            <i
              className="icon-pencil"
              title="Edytuj"
              onClick={toggleIsEditOpened}
            />
            <i
              className="icon-trash"
              title="Usuń"
              onClick={() => setIsDeleteItemModalOpened(true)}
            />
            <Link
              to={`/item/${item.id}`}
            >
              <i
                className={`icon-doc-text-inv ${styles.noMarginBottom}`}
                title="Szczegóły"
              />
            </Link>
          </div>
        </div>
      </div>
      {
        isEditOpened ?
          <form
            className={styles.editWrapper}
            onSubmit={(e: React.FormEvent) => {
              e.preventDefault();
              void changeOtherDataOfItemInDBAndLoc();
            }}
          >
            <div className={styles.inputWrapper}>
              <InputField
                type="text"
                name="name"
                label="Nazwa"
                labelColor="black"
                value={editValues.name}
                handleChange={onEditValueChange}
                placeholder="-"
                noErrorBar
              />
            </div>
            <div className={styles.inputWrapper}>
              <TextArea
                name="description"
                label="Opis"
                value={editValues.description}
                handleChange={onEditValueChange}
                placeholder="-"
                noErrorBar
              />
            </div>
            <div className={styles.editButtonWrapper}>
              <Button
                type="submit"
                title="Edytuj"
                backgroundColor="lightPurple"
                fontColor="black"
                isBoxShadow
              />
            </div>
          </form>
        :
          null
      }
      {
        isErrorDuplicatedName ?
          <div className={styles.errorMessageWrap}>
            <MessageBox
              message="Zduplikowana nazwa - wybierz inną"
              onCloseClick={() => setIsErrorDuplicatedName(false)}
              wide
              isError
            />
          </div>
        :
          null
      }
      <DeleteItemModal
        itemIdx={item.id}
        isOpened={isDeleteItemModalOpened}
        locallyDeleteItem={locallyDeleteItem}
        closeModal={() => setIsDeleteItemModalOpened(false)}
      />
    </div>
  );
};

export default TodoItem;