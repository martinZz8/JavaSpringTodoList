import React, {useEffect, useState} from "react";
import axios from "axios";

// data
import {initialAddItemForm} from "./add-item-modal.types";

// interfaces
import {IAddItemForm} from "./add-item-modal.data";
import {IItem} from "../main-page-content.types";

const ADD_ITEM_API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
    `${process.env.REACT_APP_BACKEND_API_URL}todo_item/create`
  :
    null;

const useAddItemModal = (
  isOpened: boolean,
  locallyAddItem: (item: IItem) => void,
  closeModal: () => void
) => {
  const [addItemForm, setAddItemForm] = useState<IAddItemForm>(initialAddItemForm);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isDuplicatedNameError, setIsDuplicatedNameError] = useState<boolean>(false);

  useEffect(() => {
    setAddItemForm(initialAddItemForm);
  },[isOpened]);

  const onInputChange = (name: string, value: string | boolean) => {
    setAddItemForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const addItemToDBAndLoc = async (e: React.FormEvent) => {
    e.preventDefault();

    // Perform addition of item
    if (ADD_ITEM_API_URL) {
      setIsLoading(true);

      try {
        const {data} = await axios.post(ADD_ITEM_API_URL, {
          name: addItemForm.name,
          description: addItemForm.description,
          isDone: addItemForm.isDone
        }, {
          headers: {
            "Content-Type": "application/json"
          }
        });

        // close modal & locally add item
        closeModal();
        locallyAddItem(data as IItem);
        setIsDuplicatedNameError(false);
      }
      catch(e: any) {
        console.log("Error during addition of new data - duplicated name");
        setIsDuplicatedNameError(true);
      }
      finally {
        setIsLoading(false);
      }
    }
  };

  return {
    addItemForm,
    onInputChange,
    addItemToDBAndLoc,
    isLoading,
    isDuplicatedNameError,
    setIsDuplicatedNameError
  };
};

export default useAddItemModal;