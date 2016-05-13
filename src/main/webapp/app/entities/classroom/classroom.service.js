(function() {
    'use strict';
    angular
        .module('hopeRanchLearningAcademyApp')
        .factory('Classroom', Classroom);

    Classroom.$inject = ['$resource'];

    function Classroom ($resource) {
        var resourceUrl =  'api/classrooms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
