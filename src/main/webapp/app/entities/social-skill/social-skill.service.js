(function() {
    'use strict';
    angular
        .module('hopeRanchLearningAcademyApp')
        .factory('Social_skill', Social_skill);

    Social_skill.$inject = ['$resource'];

    function Social_skill ($resource) {
        var resourceUrl =  'api/social-skills/:id';

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
