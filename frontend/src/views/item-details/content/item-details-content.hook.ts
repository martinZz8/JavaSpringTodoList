import {useEffect, useState} from "react";
import axios from "axios";

// interfaces
import {IItem} from "../../main-page/content/main-page-content.types";

const useItemDetailsContent = (idStr: number) => {
  const GET_ITEM_API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
    `${process.env.REACT_APP_BACKEND_API_URL}todo_item/get/${idStr}`
  :
    null;

  const [item, setItem] = useState<IItem | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isItemNotFound, setIsItemNotFound] = useState<boolean>(false);

  useEffect(() => {
    void getItemById();
  },[]);

  const getItemById = async () => {
    if (GET_ITEM_API_URL) {
      setIsLoading(true);
      try {
        const {data} = await axios.get(GET_ITEM_API_URL);
        setItem({
          id: data.id,
          name: data.name,
          description: data.description,
          createdOn: new Date(data.createdOn),
          done: data.done
        });
      }
      catch(e: any) {
        console.log("Item with specified id was not found");
        setIsItemNotFound(true);
      }
      finally {
        setIsLoading(false);
      }
    }
  };

  return {
    item,
    isLoading,
    isItemNotFound
  }
};

export default useItemDetailsContent;