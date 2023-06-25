// params:
// monthNum - has to be number from 1 to 12
export const getTwoDigitsMonthStr = (monthNum: number): string => {
  let strToRet = "";

  if (monthNum < 10) {
    strToRet += "0";
  }
  strToRet += monthNum.toString();

  return strToRet;
};