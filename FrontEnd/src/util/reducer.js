export const reducer = (state, action) => {
  return {
    ...state,
    [action.id]: action.value
  }
}