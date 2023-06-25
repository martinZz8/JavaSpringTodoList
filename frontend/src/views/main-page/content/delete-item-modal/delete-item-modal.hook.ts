import React, {useState} from "react";
import axios from "axios";

const useDeleteItemModal = (
    itemIdx: number,
    locallyDeleteItem: () => void,
    closeModal: () => void
  ) => {
  const API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
      `${process.env.REACT_APP_BACKEND_API_URL}todo_item/delete/${itemIdx}`
    :
      null;

  const [isLoading, setIsLoading] = useState<boolean>(false);

  const performDeletionOfItem = async (e: React.FormEvent) => {
    e.preventDefault();

    // Perform deletion of item
    if (API_URL) {
      setIsLoading(true);

      try {
        await axios.delete(API_URL);

        // Close modal & locally delete item
        closeModal();
        locallyDeleteItem();
      }
      catch(e: any) {
        console.log("Error during deleting of item");
      }
      finally {
        setIsLoading(false);
      }
    }
  };

  return {
    isLoading,
    performDeletionOfItem
  }
};

export default useDeleteItemModal;