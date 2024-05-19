export const useLocalStorage = (key: string) => {
    const setItem = (value: unknown) => {
        try {
            window.localStorage.setItem(key, JSON.stringify(value));
        } catch (error) {
            console.log('error set value to local storage');
        }
    };

    const getItem = () => {
        try {
            const value = window.localStorage.getItem(key);
            return value ? JSON.parse(value) : undefined;
        } catch (error) {
            console.log('error get value from local storage');
        }
    };

    const deleteItem = () => {
        try {
            window.localStorage.removeItem(key);
        } catch (error) {
            console.log('error delete value from local storage');
        }
    };
    return { setItem, getItem, deleteItem };
};
