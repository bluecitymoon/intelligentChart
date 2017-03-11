(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('EducationLevel', EducationLevel);

    EducationLevel.$inject = ['$resource'];

    function EducationLevel ($resource) {
        var resourceUrl =  'api/education-levels/:id';
        var findByPersonUrl =  'api/education-levels/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
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
