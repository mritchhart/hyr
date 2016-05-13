(function() {
    'use strict';
    angular
        .module('hopeRanchLearningAcademyApp')
        .factory('Point_entry', Point_entry)
        .factory('Point_Sums', Point_Sums)
        .factory('Skill_Data', Skill_Data)
        .factory('Skill_Data_Daily', Skill_Data_Daily)
        .factory('Skill_Data_Custom', Skill_Data_Custom);

    Point_entry.$inject = ['$resource', 'DateUtils'];
    Point_Sums.$inject = ['$resource', 'DateUtils'];
    Skill_Data.$inject = ['$resource', 'DateUtils'];
    Skill_Data_Daily.$inject = ['$resource', 'DateUtils'];
    Skill_Data_Custom.$inject = ['$resource', 'DateUtils'];

    function Point_entry ($resource, DateUtils) {
        var resourceUrl =  'api/point-entries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.ent_submission_time = DateUtils.convertDateTimeFromServer(data.ent_submission_time);
                    data.ent_action_time = DateUtils.convertDateTimeFromServer(data.ent_action_time);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
    function Point_Sums ($resource, DateUtils) {
        return $resource('api/pointSum/:stuId/:skillId', {}, {
            'query': { method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
//                        console.log(data);
                    var number = data;
                    data = {};
                    data.skillPointSum = number;

                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
    function Skill_Data ($resource, DateUtils) {
        return $resource('api/skillData/:stuId/:skillId', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data.length > 2){
                        data = angular.fromJson(data);
                        var skillOBJ = {};
                        console.log(data);
                        skillOBJ.points = data[0][0];
                        skillOBJ.skillName = data[0][1];
                        skillOBJ.skillId = data[0][2];
//                            console.log(skillOBJ);
                        return skillOBJ;
                    } else {
                        console.log("DATA is NULL");
                        console.log(data);
                        var skillOBJ = {};
                        skillOBJ.points = null;
                        skillOBJ.skillName = null;
                        skillOBJ.skillId = null;
                        return skillOBJ;
                    }
                }
            },
            'update': { method:'PUT' }
        });
    }
    function Skill_Data_Daily ($resource, DateUtils) {
        return $resource('api/skillData/daily/:stuId/:skillId', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                console.log(data.length);
                    if (data.length > 2){
                        data = angular.fromJson(data);
                        var skillOBJ = {};
                        console.log(data);
                        skillOBJ.points = data[0][0];
                        skillOBJ.skillName = data[0][1];
                        skillOBJ.skillId = data[0][2];
//                            console.log(skillOBJ);
                        return skillOBJ;
                    } else {
                        console.log("DATA is NULL");
                        var skillOBJ = {};
                        skillOBJ.points = null;
                        skillOBJ.skillName = null;
                        skillOBJ.skillId = null;
                        return skillOBJ;
                    }
                }
            },
            'update': { method:'PUT' }
        });
    }
    function Skill_Data_Custom ($resource, DateUtils) {
        return $resource('api/skillData/custom/:stuId/:skillId/:startDateRaw/:endDateRaw', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                console.log(data.length);
                    if (data.length > 2){
                        data = angular.fromJson(data);
                        var skillOBJ = {};
                        console.log(data);
                        skillOBJ.points = data[0][0];
                        skillOBJ.skillName = data[0][1];
                        skillOBJ.skillId = data[0][2];
//                            console.log(skillOBJ);
                        return skillOBJ;
                    } else {
                        console.log("DATA is NULL");
                        var skillOBJ = {};
                        skillOBJ.points = null;
                        skillOBJ.skillName = null;
                        skillOBJ.skillId = null;
                        return skillOBJ;
                    }
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
