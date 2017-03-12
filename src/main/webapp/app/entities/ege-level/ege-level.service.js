(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('EgeLevel', EgeLevel);

    EgeLevel.$inject = ['$resource'];

    function EgeLevel ($resource) {
        var resourceUrl =  'api/ege-levels/:id';
        var findByPersonUrl =  'api/ege-levels/person/:id';
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
