(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('EducationBackgroundType', EducationBackgroundType);

    EducationBackgroundType.$inject = ['$resource'];

    function EducationBackgroundType ($resource) {
        var resourceUrl =  'api/education-background-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
