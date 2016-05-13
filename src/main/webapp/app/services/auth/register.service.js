(function () {
    'use strict';

    angular
        .module('hopeRanchLearningAcademyApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
