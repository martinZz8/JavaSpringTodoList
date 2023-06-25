import {useState, useEffect} from "react";
import axios from "axios";

// interfaces
import {IEditData} from "./todo-item.types";
import {IItem} from "../main-page-content.types";

const useTodoItem = (
    item: IItem,
    locallyChangeItem: (value: IItem) => void
  ) => {
  const CHANGE_STATUS_API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
    `${process.env.REACT_APP_BACKEND_API_URL}todo_item/update_status/${item.id}`
  :
    null;

  const CHANGE_OTHER_DATA_API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
    `${process.env.REACT_APP_BACKEND_API_URL}todo_item/update/${item.id}`
  :
    null;

  const [editValues, setEditValues] = useState<IEditData>({
    name: item.name,
    description: item.description
  });
  const [isEditOpened, setIsEditOpened] = useState<boolean>(false);
  const [isErrorDuplicatedName, setIsErrorDuplicatedName] = useState<boolean>(false);
  const [isDeleteItemModalOpened, setIsDeleteItemModalOpened] = useState<boolean>(false);

  useEffect(() => {
    setEditValues({
      name: item.name,
      description: item.description
    });

    setIsEditOpened(false);
  },[item]);

  const toggleIsEditOpened = () => {
    setIsEditOpened(prev => !prev);
  };

  const onEditValueChange = (name: string, value: string) => {
    setEditValues(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const changeStatusOfItemInDBAndLoc = async (newStatus: boolean) => {
    // Perform update of status
    if (CHANGE_STATUS_API_URL) {
      try {
        await axios.put(CHANGE_STATUS_API_URL, {
          isDone: newStatus
        }, {
          headers: {
            "Content-Type": "application/json"
          }
        });

        // Locally change items
        locallyChangeItem({
          ...item,
          done: newStatus
        });
        setIsErrorDuplicatedName(false);
      }
      catch(e: any) {
        console.log("Error during edit of status");
      }
    }
  };

  const changeOtherDataOfItemInDBAndLoc = async () => {
    // Perform update of data
    if (CHANGE_OTHER_DATA_API_URL) {
      try {
        await axios.put(CHANGE_OTHER_DATA_API_URL, {
          name: editValues.name,
          description: editValues.description
        }, {
          headers: {
            "Content-Type": "application/json"
          }
        });

        // Locally change items
        locallyChangeItem({
          ...item,
          name: editValues.name,
          description: editValues.description
        });
        setIsErrorDuplicatedName(false);
      }
      catch(e: any) {
        console.log("Error during edit of data");

        if (e.response) {
          const mess = e.response.data.message;

          if (mess !== "") {
            console.log("Message: ", mess);

            setEditValues({
              name: item.name,
              description: item.description
            });
            setIsErrorDuplicatedName(true);
          }
        }
      }
    }
  };

  return {
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
  };
};

export default useTodoItem;