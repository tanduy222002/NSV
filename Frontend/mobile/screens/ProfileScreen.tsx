import { SafeAreaView, Text } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { BottomTabNavigationProp } from '@react-navigation/bottom-tabs';
import { Button } from '../components';
import { useAsyncStorage } from '@react-native-async-storage/async-storage';
import { useAppDispatch } from '../hooks';
import { loggedOut } from '../store/slices/auth/authSlice';
import { RootTabParamList } from '../type';

const ProfileScreen = () => {
    const navigation =
        useNavigation<BottomTabNavigationProp<RootTabParamList>>();
    const dispatch = useAppDispatch();
    const { removeItem: removeAccessToken } = useAsyncStorage('access-token');
    const { removeItem: removeRefreshToken } = useAsyncStorage('refresh-token');

    const handleLogout = async () => {
        await removeAccessToken();
        await removeRefreshToken();
        dispatch(loggedOut());
        console.log('lougout success');
    };
    return (
        <SafeAreaView className="flex flex-col items-center">
            <Text>Profile</Text>
            <Button
                title="Đăng xuất"
                className="bg-red-500"
                textStyle="text-white"
                onPress={handleLogout}
            />
        </SafeAreaView>
    );
};

export default ProfileScreen;
