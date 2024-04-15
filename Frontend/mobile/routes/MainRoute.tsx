import { ReactNode } from 'react';
import { TouchableOpacity } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import AntDesignIcons from 'react-native-vector-icons/AntDesign';
import FeatherIcons from 'react-native-vector-icons/Feather';
import {
    ProfileScreen,
    WarehouseScreen,
    QrScreen,
    TicketScreen,
    ProductScreen
} from '../screens';

const Tab = createBottomTabNavigator();

type ScanQrButtonProps = {
    children: ReactNode;
    onPress?: (e : any) => void;
};

const ScanQrButton = ({ children, onPress }: ScanQrButtonProps) => {
    return (
        <TouchableOpacity
            onPress={onPress}
            className="w-[70px] h-[70px] rounded-full bg-blue-600 -translate-y-[40px]"
        >
            {children}
        </TouchableOpacity>
    );
};

const MainRoute = () => {
    return (
        <Tab.Navigator
            screenOptions={{
                tabBarShowLabel: false,
                tabBarStyle: {
                    position: 'absolute',
                    left: 0,
                    right: 0,
                    backgroundColor: '#ffffff',
                    borderTopLeftRadius: 20,
                    borderTopRightRadius: 20,
                    paddingTop: 15,
                    paddingBottom: 15,
                    height: 70
                }
            }}
        >
            <Tab.Screen
                name="Warehouse"
                component={WarehouseScreen}
                options={{
                    tabBarIcon: (focused) => (
                        <MaterialIcons name="warehouse" size={28} />
                    )
                }}
            />
            <Tab.Screen
                name="Ticket"
                component={TicketScreen}
                options={{
                    tabBarIcon: (focused) => (
                        <FeatherIcons name="package" size={28} />
                    )
                }}
            />
            <Tab.Screen
                name="Qr"
                component={QrScreen}
                options={{
                    tabBarIcon: (focused) => (
                        <MaterialIcons name="qr-code-scanner" size={28} />
                    ),
                    tabBarButton: (props) => <ScanQrButton {...props} />
                }}
            />
            <Tab.Screen
                name="Product"
                component={ProductScreen}
                options={{
                    tabBarIcon: (focused) => (
                        <MaterialCommunityIcons
                            name="fruit-pineapple"
                            size={28}
                        />
                    )
                }}
            />
            <Tab.Screen
                name="Profile"
                component={ProfileScreen}
                options={{
                    tabBarIcon: (focused) => (
                        <AntDesignIcons name="user" size={28} />
                    )
                }}
            />
        </Tab.Navigator>
    );
};

export default MainRoute;
