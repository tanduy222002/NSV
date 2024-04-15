import AsyncStorage from '@react-native-async-storage/async-storage';

const useAsyncStorage = (key: string) => {
    const getItem = async () => {
        try {
            const data = await AsyncStorage.getItem(key);
            return data;
        } catch (error) {
            // Error saving data
            console.log('error getting value from async storage: ', error);
        }
    };
    const setItem = async (value: string) => {
        try {
            await AsyncStorage.setItem(key, value);
        } catch (error) {
            // Error saving data
            console.log('error setting value from async storage: ', error);
        }
    };
    const removeItem = async () => {
        try {
            await AsyncStorage.removeItem(key);
        } catch (error) {
            // Error saving data
            console.log('error remove value from async storage: ', error);
        }
    };

    return { getItem, setItem, removeItem };
};

export default useAsyncStorage;
