import React, {useContext, useState} from "react";
import axios from "axios";

// contexts
import {LoginContext} from "../../../../providers/login/login-provider.component";

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

  const {bearerToken} = useContext(LoginContext);

  const performDeletionOfItem = async (e: React.FormEvent) => {
    e.preventDefault();

    // Perform deletion of item
    if (API_URL) {
      setIsLoading(true);

      try {
        await axios.delete(API_URL, {
          headers: {
            Authorization: "Bearer "+bearerToken
          }
        });

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