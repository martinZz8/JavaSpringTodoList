import {useContext, useEffect, useState} from "react";
import axios from "axios";

// contexts
import {LoginContext} from "../../../providers/login/login-provider.component";

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

  const {bearerToken} = useContext(LoginContext);

  useEffect(() => {
    void getItemById();
  },[]);

  const getItemById = async () => {
    if (GET_ITEM_API_URL) {
      setIsLoading(true);
      try {
        const {data} = await axios.get(GET_ITEM_API_URL, {
          headers: {
            Authorization: "Bearer "+bearerToken
          }
        });
        setItem({
          id: data.id,
          name: data.name,
          description: data.description,
          createdOn: new Date(data.createdOn),
          done: data.done
        });
      }
      catch(e: any) {
        if (e.response) {
          if (e.response.status === 404) {
            console.log("Item with specified id was not found");
          }
          else {
            // 403 (forbidden) status code
            console.log("Item was found, but you don't have authority to watch it");
          }
        }
        else {
          console.log("Unexpected error occurred. Try again later.");
        }

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