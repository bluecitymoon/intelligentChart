(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonTieBa', PersonTieBa);

    PersonTieBa.$inject = ['$resource'];

    function PersonTieBa ($resource) {
        var resourceUrl =  'api/person-tie-bas/:id';

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
                url: 'api/person-tie-bas/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
