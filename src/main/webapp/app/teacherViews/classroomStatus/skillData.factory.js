'use strict';

angular.module('hopeRanchLearningAcademyApp')
    .factory('skillDataService', function() {

        var skillDataService = {};

        skillDataService.savedData = {};

        skillDataService.set = function(data) {
            skillDataService.savedData = data;
            console.log("Data set");
            //console.log(data);
            //localStorageService.set('currentSkillData', data);
        };

        skillDataService.get = function() {
            //var storedData = localStorageService.get('currentSkillData');
            //console.log(storedData);
            return skillDataService.savedData;
        };

        return skillDataService;


    });
