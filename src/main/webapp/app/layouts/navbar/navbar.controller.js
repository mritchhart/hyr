(function() {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ENV', 'LoginService', '$rootScope'];

    function NavbarController ($state, Auth, Principal, ENV, LoginService, $rootScope) {
        var vm = this;

        $rootScope.$state = $state;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.inProduction = ENV === 'prod';
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login () {
            collapseNavbar();
            LoginService.open();
        }

        function logout () {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar () {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar () {
            vm.isNavbarCollapsed = true;
        }


        $(document).ready(function() {
            $.backstretch("content/images/Horse-Farm.jpg");
        });
    }
})();
