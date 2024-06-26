import { HashRouter, Routes, Route } from 'react-router-dom';
import LoginForm from './features/auth/LoginForm';
import RegisterForm from './features/auth/RegisterForm';
import RenewPasswordForm from './features/auth/RenewPasswordForm';
import ForgotPasswordForm from './features/auth/ForgotPasswordForm';
import RenewSuccess from './features/auth/RenewSuccess';
import HomePage from './pages/HomePage';
import {
    AuthPage,
    ProductPage,
    WareHousePage,
    ExportPage,
    CreateExportTicketPage,
    ImportPage,
    CreateWarehousePage,
    CreateWarehouseMapPage,
    ProductLocationDetailPage,
    CreateProductPage,
    EditProductPage,
    PartnerPage,
    CreateImportTicketPage,
    ImportTicketDetailPage,
    WarehouseDetailPage,
    WarehouseSlotDetailPage,
    ExportTicketDetailPage,
    PartnerDetailPage,
    CreatePartnerPage,
    EmployeePage,
    EmployeeDetailPage,
    UserProfilePage,
    EditUserProfilePage,
    EditPartnerDetailPage
} from './pages';
import { Provider } from 'react-redux';
import { store } from './store/store';
import { Sidebar } from './layouts';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const client = new QueryClient({
    defaultOptions: {
        queries: {
            refetchOnWindowFocus: false // default: true
        }
    }
});

function App(): JSX.Element {
    return (
        <Provider store={store}>
            <QueryClientProvider client={client}>
                <HashRouter>
                    <div className="min-w-screen min-h-screen">
                        <Routes>
                            {/* auth route */}
                            <Route path="/auth" element={<AuthPage />}>
                                <Route path="login" element={<LoginForm />} />
                                <Route
                                    path="register"
                                    element={<RegisterForm />}
                                />
                                <Route
                                    path="forgot-password"
                                    element={<ForgotPasswordForm />}
                                />
                                <Route
                                    path="renew-password/:identifier"
                                    element={<RenewPasswordForm />}
                                />
                                <Route
                                    path="renew-success"
                                    element={<RenewSuccess />}
                                />
                            </Route>
                            {/* main route */}
                            <Route path="/" element={<Sidebar />}>
                                <Route index element={<HomePage />} />
                                <Route
                                    path="/warehouse"
                                    element={<WareHousePage />}
                                />
                                <Route
                                    path="/warehouse/:id"
                                    element={<WarehouseDetailPage />}
                                />
                                <Route
                                    path="/warehouse/:id/slot/:slotId"
                                    element={<WarehouseSlotDetailPage />}
                                />
                                <Route
                                    path="/warehouse/create"
                                    element={<CreateWarehousePage />}
                                />
                                <Route
                                    path="/warehouse/map/create"
                                    element={<CreateWarehouseMapPage />}
                                />
                                <Route
                                    path="/product"
                                    element={<ProductPage />}
                                />
                                <Route
                                    path="/product/:productId/location"
                                    element={<ProductLocationDetailPage />}
                                />
                                <Route
                                    path="/product/create"
                                    element={<CreateProductPage />}
                                />
                                <Route
                                    path="/product/edit"
                                    element={<EditProductPage />}
                                />
                                <Route
                                    path="/export"
                                    element={<ExportPage />}
                                />
                                <Route
                                    path="/export/create"
                                    element={<CreateExportTicketPage />}
                                />
                                <Route
                                    path="/export/:id"
                                    element={<ExportTicketDetailPage />}
                                />
                                <Route
                                    path="/import"
                                    element={<ImportPage />}
                                />
                                <Route
                                    path="/import/:id"
                                    element={<ImportTicketDetailPage />}
                                />
                                <Route
                                    path="/import/create"
                                    element={<CreateImportTicketPage />}
                                />
                                <Route
                                    path="/partner"
                                    element={<PartnerPage />}
                                />
                                <Route
                                    path="/partner/create"
                                    element={<CreatePartnerPage />}
                                />
                                <Route
                                    path="/partner/:id"
                                    element={<PartnerDetailPage />}
                                />
                                <Route
                                    path="/partner/:id/edit"
                                    element={<EditPartnerDetailPage />}
                                />
                                <Route
                                    path="/employee"
                                    element={<EmployeePage />}
                                />
                                <Route
                                    path="/employee/:id"
                                    element={<EmployeeDetailPage />}
                                />
                                <Route
                                    path="/profile"
                                    element={<UserProfilePage />}
                                />
                                <Route
                                    path="/profile/edit"
                                    element={<EditUserProfilePage />}
                                />
                            </Route>
                        </Routes>
                    </div>
                </HashRouter>
            </QueryClientProvider>
        </Provider>
    );
}

export default App;
