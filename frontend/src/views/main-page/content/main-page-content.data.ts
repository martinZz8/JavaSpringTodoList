import {IOption} from "../../../components/ui/select/select.types";
import {IFilterValues} from "./main-page-content.types";

export const selectIsDoneOptions: IOption[] = [
  {
    id: 1,
    value: "",
    textToShow: "Wszystkie"
  },
  {
    id: 2,
    value: "true",
    textToShow: "Zrobione"
  },
  {
    id: 3,
    value: "false",
    textToShow: "Do zrobienia"
  }
];

export const selectIsOrderByDateAscendingOptions: IOption[] = [
  {
    id: 1,
    value: "",
    textToShow: "Domyślnie"
  },
  {
    id: 2,
    value: "true",
    textToShow: "Najstarsze"
  },
  {
    id: 3,
    value: "false",
    textToShow: "Najmłodsze"
  }
];

export const initialFilterValues: IFilterValues = {
  name: "",
  isDoneStr: "",
  isAscendingStr: ""
}
