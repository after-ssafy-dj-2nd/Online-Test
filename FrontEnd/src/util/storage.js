export function storage(storageType) {
  return {
    getItem: (key) => {
      // 추후 JSON.parse() 메서드로 value 값을 파싱해서 가져와야 하는 경우
      // 해당 key 값을 parseKeys 배열에 추가하면 됨
      // ex) 배열이나 객체 형태 값이 들어오는 경우
      const parseKeys = ['loginStatus', 'userInfo'];
      const value = storageType.getItem(key);
      if (parseKeys.includes(key)) {
        return JSON.parse(value);
      } else {
        return value;
      }
    },
    setItem: (key, value) => {
      // 추후 storage에 저장하는 값들이 배열이나 객체인 경우 JSON.stringify() 메서드를 사용해야하므로
      // 해당 key 값을 stringifyKeys 배열에 추가하면 됨
      const stringifyKeys = ['loginStatus', 'userInfo'];
      if (stringifyKeys.includes(key)) {
        storageType.setItem(key, JSON.stringify(value));
      } else {
        storageType.setItem(key, value);
      }
    },
    removeItem: (key) => {
      storageType.removeItem(key);
    }
  }
}