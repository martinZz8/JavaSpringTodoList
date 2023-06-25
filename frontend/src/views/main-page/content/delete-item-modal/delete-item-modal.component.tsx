import React from "react";

// hooks
import useDeleteItemModal from "./delete-item-modal.hook";

// components
import TemplateActionModal from "../../../../modals/action-modal/action-modal.template";

// interfaces
interface IDeleteItemModal {
  itemIdx: number;
  isOpened: boolean;
  locallyDeleteItem: () => void;
  closeModal: () => void;
}

const DeleteItemModal: React.FC<IDeleteItemModal> = ({
    itemIdx,
    isOpened,
    locallyDeleteItem,
    closeModal
 }) => {
  const {isLoading, performDeletionOfItem} = useDeleteItemModal(itemIdx, locallyDeleteItem, closeModal);

  return (
    <TemplateActionModal
      title="Czy chcesz usunąć zadanie?"
      isOpened={isOpened}
      isLoading={isLoading}
      onSubmitClick={performDeletionOfItem}
      onCancelClick={closeModal}
      submitButtonTitle="Tak"
      cancelButtonTitle="Nie"
    >
      <></>
    </TemplateActionModal>
  );
};

export default DeleteItemModal;