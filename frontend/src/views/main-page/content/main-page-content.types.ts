export interface IItem {
  id: number;
  name: string;
  description: string;
  done: boolean;
  createdOn: Date;
}

export interface IFilterValues {
  name: string; // blank means no filter
  isDoneStr: string; // blank means no filter
  isAscendingStr: string; // blank means no filter
}
