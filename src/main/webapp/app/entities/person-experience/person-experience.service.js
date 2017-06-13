(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonExperience', PersonExperience);

    PersonExperience.$inject = ['$resource'];

    function PersonExperience ($resource) {
        var resourceUrl =  'api/person-experiences/:id';

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
            'update': { method:'PUT' },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-experiences/person/all/:id',
                params: { id : '@id'}
            }
        });
    }
})();
